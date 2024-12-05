package com.alura.LiterAlura.model;

public enum Idioma {
    ES("es"),
    EN("en"),
    FR("fr"),
    PT("pt");

    private String idioma;

    Idioma(String idioma){
        this.idioma = idioma;
    }

    public static Idioma fromString(String text){
        System.out.println("texto a idioma: "+ text);
        for (Idioma idioma : Idioma.values()){
            if (idioma.idioma.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("No existe el idioma: " + text + " en el sistema!");
    }
}
