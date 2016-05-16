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

  public ASTreeNode parse(String input) {

    Tokenizer tk = new Tokenizer(input);
    tokenStream = tk.getTokenStream();

    ASTreeNode root = parse();

    return root;
  }

  public boolean testParse(String input) {

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

    ASTreeNode rectCommandNode = parseRectCommand();

    if (rectCommandNode != null) {
      return rectCommandNode;
    }

    ASTreeNode circleCommandNode = parseCircleCommand();

    if (circleCommandNode != null) {
      return circleCommandNode;
    }

    return null;
  }

  public ASTreeNode parseCircleCommand() {

    if (!parseCircleSymbol()) {
      return null;
    }

    int argumentCount = 4;
    CircleCommandNode circleNode = new CircleCommandNode(null);
    ASTreeNode expArgs[] = new ASTreeNode[argumentCount];

    for (int i = 1; i <= argumentCount; i++) {

      currentToken = tokenStream.getNext();

      expArgs[i - 1] = parseExpression();
      if (expArgs[i - 1] == null) {

        System.out.println("Error! parseCircleCommand: parseExpression");

        //pushBack previous token
        tokenStream.pushBack(i);
        //set current token to the previous token
        currentToken = tokenStream.getNext();

        return null;
      }

      circleNode.arguments = expArgs;
    }

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseCircleCommand: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    return circleNode;
  }

  public ASTreeNode parseRectCommand() {

    if (!parseRectSymbol()) {
      return null;
    }

    int argumentCount = 5;
    RectCommandNode rectNode = new RectCommandNode(null);
    ASTreeNode expArgs[] = new ASTreeNode[argumentCount];

    for (int i = 1; i <= argumentCount; i++) {

      currentToken = tokenStream.getNext();

      expArgs[i - 1] = parseExpression();
      if (expArgs[i - 1] == null) {

        System.out.println("Error! parseRectCommand: parseExpression");

        //pushBack previous token
        tokenStream.pushBack(i);
        //set current token to the previous token
        currentToken = tokenStream.getNext();

        return null;
      }

      rectNode.arguments = expArgs;
    }

    currentToken = tokenStream.getNext();
    if (!parseSemicolon()) {
      System.out.println("Error! parseRectCommand: parseSemicolon");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    return rectNode;
  }

  public ASTreeNode parseInitialization() {

    DeclarationNode declareIntNode = null;
    BinaryOperatorNode equalNode = null;

    if (!parseBasicType()) {
      return null;
    }

    declareIntNode = new DeclarationNode(currentToken.getValue(), null);

    currentToken = tokenStream.getNext();

    equalNode = (BinaryOperatorNode) parseAssignment();

    if (equalNode == null) {
      System.out.println("Error! parseInitialization: parseAssignment");

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    String identifierName = ((IdentifierNode) (equalNode.left)).name;
    declareIntNode.identifier = identifierName;

    //((BinaryOperatorNode) equalNode).setLeftNode(declareIntNode);
    equalNode.left = declareIntNode;

    return equalNode;
  }

  public ASTreeNode parseAssignment() {

    ASTreeNode idNode = null;
    BinaryOperatorNode equalNode = null;

    if (!parseIdentifier()) {
      return null;
    }

    idNode = new IdentifierNode(currentToken.getValue());

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

    equalNode = new EqualNode(idNode, null);

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

    equalNode.right = expNode;

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

    BinaryOperatorNode expFactoredNode = null;
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

    expFactoredNode = (BinaryOperatorNode) parseExpressionFactored();
    if (expFactoredNode == null) {
      //pushback one tokens
      tokenStream.pushBack(1);
      //set currentToken to next token
      currentToken = tokenStream.getNext();
      return null;
    }

    //create Expression -> Term ExpressionFactored
    //((BinaryOperatorNode) expFactoredNode).setLeftNode(termNode);
    expFactoredNode.left = termNode;

    return expFactoredNode;
  }

  public ASTreeNode parseExpressionFactored() {

    BinaryOperatorNode binaryOpNode = null;
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

    //((BinaryOperatorNode) binaryOpNode).setRightNode(expNode);
    binaryOpNode.right = expNode;

    return binaryOpNode;
  }

  public ASTreeNode parseTerm() {

    ASTreeNode factorNode = parseFactor();
    BinaryOperatorNode termFactoredNode = null;

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

    termFactoredNode = (BinaryOperatorNode) parseTermFactored();
    if (termFactoredNode == null) {

      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    //create Term -> Factor  (*|/) Term
    //((BinaryOperatorNode) termFactoredNode).setLeftNode(factorNode);
    termFactoredNode.left = factorNode;

    return termFactoredNode;
  }

  public ASTreeNode parseTermFactored() {

    BinaryOperatorNode binaryOpNode = null;
    ASTreeNode termNode = null;

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
    termNode = parseTerm();
    if (termNode == null) {
      //pushBack previous token
      tokenStream.pushBack(1);
      //set current token to the previous token
      currentToken = tokenStream.getNext();

      return null;
    }

    //binaryOpNode.setRightNode(termNode);
    binaryOpNode.right = termNode;

    return binaryOpNode;
  }

  public ASTreeNode parseFactor() {

    ASTreeNode varNode = null;
    ASTreeNode expNode = null;
    ASTreeNode literalNode = null;

    if (!parseLiteralInteger()) {
      if (!parseIdentifier()) {
        if (!parseLeftParenthesis()) {
          return null;
        }

        //get next token so we skip parenthesis, or else stackoverflow
        currentToken = tokenStream.getNext();
        expNode = parseExpression();

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
      varNode = new IdentifierNode(varName);

      return varNode;
    }

    int value = Integer.parseInt(currentToken.getValue());
    literalNode = new LiteralIntegerNode(value);

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
