/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author Flex
 */
public class ParserTree {

  private TokenStream tokenStream;
  private Token currentToken;

  public ParserTree() {

  }

  public boolean parse(String input) {

    Tokenizer tk = new Tokenizer(input);
    tokenStream = tk.getTokenStream();

    ASTreeNode root = parse();
    System.out.println(root);

    return root != null;
  }

  private ASTreeNode parse() {

    ASTreeNode root = null;

    while (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();

      root = parseStatement();
    }

    return root;
  }

  public ASTreeNode parseStatement() {

    ASTreeNode assignmentNode = parseAssignment();

    if (assignmentNode != null) {
      return assignmentNode;
    }

    ASTreeNode initNode = parseInitialization();

    if (initNode != null) {
      return initNode;
    }

    return null;
  }

  public ASTreeNode parseInitialization() {
    if (!parseBasicType()) {
      return null;
    }

    ASTreeNode declareIntNode = new DeclarationNode(currentToken.getValue(), null);

    currentToken = tokenStream.getNext();

    ASTreeNode equalNode = parseAssignment();

    if (equalNode == null) {
      System.out.println("Error! parseInitialization: parseAssignment");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    String identifierName = ((IdentifierNode) ((BinaryOperatorNode) equalNode).left).name;
    ((DeclarationNode) declareIntNode).identifier = identifierName;

    ((BinaryOperatorNode) equalNode).setLeftNode(declareIntNode);

    return equalNode;
  }

  public ASTreeNode parseAssignment() {

    if (!parseIdentifier()) {
      return null;
    }

    ASTreeNode idNode = new IdentifierNode(currentToken.getValue());

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return null;
    }

    if (!parseEqualSymbol()) {
      System.out.println("Error! parseAssignment: parseEqualSymbol");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    ASTreeNode equalNode = new EqualNode(idNode, null);

    currentToken = tokenStream.getNext();

    ASTreeNode expNode = parseExpression();
    if (expNode == null) {
      System.out.println("Error! parseAssignment: parseExpression");

      //pushBack previous 2 tokens
      tokenStream.pushBack(2);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    ((BinaryOperatorNode) equalNode).setRightNode(expNode);

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseAssignment: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    return equalNode;
  }

  public ASTreeNode parseExpression() {

    ASTreeNode termNode = parseTerm();

    if (termNode == null) {
      System.out.println("term node null");

      return null;
    }

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return null;
    }

    //handle epsilon case where the following can follow an Expression
    if (parseSemicolon()
            || parseRightParenthesis()
            || parseLeftParenthesis()
            || parseLiteralInteger()
            || parseIdentifier()) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      //returning true means Expression -> Term is true
      return termNode;
    }

    ASTreeNode expFactoredNode = parseExpressionFactored();
    if (expFactoredNode == null) {
      //pushback one tokens
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();
      return null;
    }

    //create Expression -> Term ExpressionFactored
    ((BinaryOperatorNode) expFactoredNode).setLeftNode(termNode);

    return expFactoredNode;
  }

  public ASTreeNode parseExpressionFactored() {

    ASTreeNode binaryOpNode = null;
    ASTreeNode expNode = null;

    if (!parsePlusSymbol()) {
      if (!parseMinusSymbol()) {
        return null;
      }

      binaryOpNode = new MinusNode(null, null);
    }

    if (binaryOpNode == null) {
      binaryOpNode = new PlusNode(null, null);
    }

    currentToken = tokenStream.getNext();
    expNode = parseExpression();
    if (expNode == null) {
      //pushback one token
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();

      return null;
    }

    ((BinaryOperatorNode) binaryOpNode).setRightNode(expNode);

    return binaryOpNode;
  }

  public ASTreeNode parseTerm() {

    ASTreeNode factorNode = parseFactor();
    ASTreeNode termFactoredNode = null;

    if (factorNode == null) {
      System.out.println("Null factor node");
      return null;
    }

    if (tokenStream.hasNext()) {
      currentToken = tokenStream.getNext();
    } else {
      return null;
    }

    //handle epsilon case where the following can follow Term
    if (parseSemicolon()
            || parsePlusSymbol()
            || parseMinusSymbol()
            || parseRightParenthesis()
            || parseLeftParenthesis()
            || parseLiteralInteger()
            || parseIdentifier()) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      //returning true means Term -> Factor
      return factorNode;
    }

    termFactoredNode = parseTermFactored();
    if (termFactoredNode == null) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    //create Term -> Factor  (*|/) Term
    ((BinaryOperatorNode) termFactoredNode).setLeftNode(factorNode);

    return termFactoredNode;
  }

  public ASTreeNode parseTermFactored() {

    BinaryOperatorNode binaryOpNode = null;

    if (!parseMultiplySymbol()) {
      if (!parseDivideSymbol()) {
        return null;
      }

      binaryOpNode = new DivideNode(null, null);
    }

    if (binaryOpNode == null) {
      binaryOpNode = new MultiplyNode(null, null);
    }

    currentToken = tokenStream.getNext();
    ASTreeNode termNode = parseTerm();
    if (termNode == null) {
      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    binaryOpNode.setRightNode(termNode);

    return binaryOpNode;
  }

  public ASTreeNode parseFactor() {

    if (!parseLiteralInteger()) {
      if (!parseIdentifier()) {
        if (!parseLeftParenthesis()) {
          return null;
        }

        //get next token so we skip parenthesis, or else stackoverflow
        currentToken = tokenStream.getNext();
        ASTreeNode expNode = parseExpression();

        if (expNode == null) {
          //error()
          System.out.println("Error! parseFactor: parseExpression");

          //pushBack previous token
          tokenStream.pushBack(1);
          //set current token to the previous token
          currentToken = tokenStream.getNext();

          return null;
        }

        currentToken = tokenStream.getNext();
        if (!parseRightParenthesis()) {
          //error()
          System.out.println("Error! parseFactor: parseRightParenthesis");

          //pushBack previous 2 tokens
          tokenStream.pushBack(2);
          //set current token to the previous token
          currentToken = tokenStream.getNext();
          return null;
        }

        return expNode;
      }

      String varName = currentToken.getValue();
      ASTreeNode varNode = new IdentifierNode(varName);

      return varNode;
    }

    int value = Integer.parseInt(currentToken.getValue());
    ASTreeNode literalNode = new LiteralIntegerNode(value);

    return literalNode;
  }

  /* Terminal parsing functions */
  public boolean parseLeftParenthesis() {
    return currentToken.getValue().equals("(");
  }

  public boolean parseRightParenthesis() {
    return currentToken.getValue().equals(")");
  }

  public boolean parseDivideSymbol() {
    return currentToken.getValue().equals("/");
  }

  public boolean parseMultiplySymbol() {
    return currentToken.getValue().equals("*");
  }

  public boolean parsePlusSymbol() {
    return currentToken.getValue().equals("+");
  }

  public boolean parseMinusSymbol() {
    return currentToken.getValue().equals("-");
  }

  public boolean parseEqualSymbol() {
    return currentToken.getValue().equals("=");
  }

  public boolean parseBasicType() {
    return currentToken.getType().equals("BasicType");
  }

  public boolean parseSemicolon() {
    return currentToken.getValue().equals(";");
  }

  public boolean parseLiteralInteger() {
    return currentToken.getType().equals("LiteralInteger");
  }

  public boolean parseIdentifier() {
    return currentToken.getType().equals("Identifier");
  }

  public boolean parseCircleSymbol() {
    return currentToken.getValue().equals("circle");

  }

  public boolean parseRectSymbol() {
    return currentToken.getValue().equals("rect");
  }
}
