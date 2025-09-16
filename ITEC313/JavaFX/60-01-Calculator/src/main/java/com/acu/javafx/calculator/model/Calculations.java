package com.acu.javafx.calculator.model;

import javafx.scene.control.Alert;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

/**
 * Calculation utilities used by both normal and scientific controllers.
 *
 * Uses exp4j to evaluate expressions typed in the input field. Handles two
 * post-processing flags: absolute value (|x|) and factorial (x!). Also provides
 * a simple integer-only guard used by the decimal-to-binary quick conversion.
 */
public class Calculations {
    public boolean isAbsoluteApplied = false;
    public boolean isFactorialApplied = false;

    /**
     * Returns true if the provided string contains only digits 0-9.
     * Used to guard the decimal-to-binary conversion feature.
     */
    public boolean controlToBin(String string){
        boolean isCharacterAllowed = false;
        String allowedCharacters = "0123456789";
        for(int j = 0; j < string.length(); j++){
            isCharacterAllowed = false;
            for(int i = 0; i < allowedCharacters.length(); i++){
                if(string.toCharArray()[j] == allowedCharacters.toCharArray()[i]){
                    isCharacterAllowed = true;
                    break;
                }
            }
            if(!isCharacterAllowed)
                break;
        }
        return isCharacterAllowed;
    }

    /**
     * Computes n! for a non-negative integer n using iterative multiplication.
     */
    public static long factorial(int number) {
        int i,factorial=1;
        for(i=1;i<=number;i++)
            factorial=factorial*i;
        return factorial;
    }

    /**
     * Shows an error alert with the provided message.
     */
    public void handleError(String message){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
    }

    /**
     * Evaluates the expression typed by the user.
     *
     * Supports optional absolute value and factorial syntaxes:
     * - Absolute value: wrap with | | (e.g., | -5 |)
     * - Factorial: suffix with ! (e.g., 5!)
     *
     * Returns a string formatted with two decimal places, or null if an error occurs.
     */
    public String evaluate(String input) throws IllegalArgumentException {
        //---------ABS----------------
        if(input.startsWith("|")){
            input = input.substring(1, input.length()-1);
            isAbsoluteApplied = true;
        }else{
            isAbsoluteApplied = false;
        }
        //---------FACTORIAL---------------
        if(input.endsWith("!")){
            input = input.substring(0, input.length()-1);
            isFactorialApplied = true;
        }else{
            isFactorialApplied = false;
        }
        Expression expression = new ExpressionBuilder(input).build();
        try {
            double result = expression.evaluate();
            if(isAbsoluteApplied && (result < 0))
                result *= -1;
            if(isFactorialApplied)
                result = factorial((int)result);
            return String.format("%.02f", result);
        } catch(UnknownFunctionOrVariableException unknown){
            handleError(unknown.toString().substring(69));
            return null;
        } catch(IllegalArgumentException invalid){
            handleError(invalid.toString().substring(35));
            return null;
        } catch(ArithmeticException arithmetic) {
            handleError(arithmetic.toString().substring(31));
            return null;
        }
    }
}


