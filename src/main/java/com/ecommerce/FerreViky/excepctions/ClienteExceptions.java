package com.ecommerce.FerreViky.excepctions;
public class ClienteExceptions {

    public static class EmailYaExisteException extends RuntimeException {
        public EmailYaExisteException(String email) {
            super("El usuario con email " + email + " ya existe");
        }
    }

    public static class ClienteNoEncontradoException extends RuntimeException {
        public ClienteNoEncontradoException(String email) {
            super("No se encontró cliente con email " + email);
        }
    }
}