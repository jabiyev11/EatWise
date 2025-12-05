package com.eatWise.exception;

public class ProfileException extends RuntimeException{

    public ProfileException(String message){
        super(message);
    }

    public  ProfileException(String message,Throwable cause){
        super(message,cause);
    }

    public static class ProfileNotFoundException extends ProfileException {
        public ProfileNotFoundException(String message){
            super(message);
        }
    }

    public static class ProfileAlreadyExistsException extends ProfileException {
        public ProfileAlreadyExistsException(String message){
            super(message);
        }
    }

    public static class ProfileNotCompleteException extends ProfileException {
        public ProfileNotCompleteException(String message){
            super(message);
        }
    }

}
