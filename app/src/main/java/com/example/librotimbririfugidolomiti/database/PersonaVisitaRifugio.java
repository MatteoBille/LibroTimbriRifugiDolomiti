package com.example.librotimbririfugidolomiti.database;

import android.view.ViewStructure;

import androidx.room.Embedded;
import androidx.room.Relation;

public class PersonaVisitaRifugio {

    @Embedded
    Persona Persona;

    @Embedded
    @Relation(parentColumn = "CodiceRifugio", entityColumn = "CodiceRifugio")
    ViewStructure vistaRifugio;

}
