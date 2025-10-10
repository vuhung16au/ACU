## Evaluating Expressions

This app evaluates arithmetic infix expressions (e.g. `(1+2)*3-4/2`) using the classic two-stack algorithm:

- **Operands stack**: stores numbers
- **Operators stack**: stores `+ - * / ( )`



# Algorithm outline

- Scan left to right. Skip spaces.
- If a number, push to operands.
- If `(`, push to operators.
- If `)`, apply operators until matching `(`.
- If operator, apply any operators with higher/equal precedence then push the new operator.
- At the end, apply remaining operators. The single remaining operand is the result.

Unary minus is handled by inserting `0` before `-` when it appears where a number is expected (e.g. `-3`, `-(1+2)`).

Run:
- `mvn test`
- `mvn clean compile`
- `mvn javafx:run`


