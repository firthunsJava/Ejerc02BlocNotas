package com.firthuns.ejerc02blocnotas.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Nota implements Parcelable {
// lo hemos hecho parcelable, poque la información tiene que ir de ventana en ventana
    private String titulo;
    private String contenido;

    public Nota(String titulo, String contenido) {
        this.titulo = titulo;
        this.contenido = contenido;
    }
// constructor vacio dado que el Parcelable lo necesita
    public Nota() {
    }

    protected Nota(Parcel in) {
        titulo = in.readString();
        contenido = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titulo);
        dest.writeString(contenido);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
