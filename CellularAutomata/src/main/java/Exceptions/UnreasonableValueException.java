package Exceptions;

public class UnreasonableValueException extends Exception{
    // custom exception to be thrown if user input values are outside reasonable bounds
    public UnreasonableValueException(){
        System.out.println("This value is unreasonable, please choose another one based on the recommendations.");
    }
}
