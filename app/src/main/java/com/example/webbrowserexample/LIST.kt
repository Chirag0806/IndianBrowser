package com.example.webbrowserexample

import java.io.Serializable

class Suggestion() : Serializable {

    lateinit var query : String
    lateinit var al : ArrayList<Suggestion>

//    init {
//        this.query = query
//        al.add(this.query)
//    }


    constructor(query : String) : this(){
//        lateinit var query : String
//        var al : ArrayList<String> = ArrayList()

        this.query = query
//        al.add(this.query)

    }

}



//class Example{
//    //Default constructor
//    Example(){
//        System.out.println("Default constructor");
//    }
//    /* Parameterized constructor with
//     * two integer arguments
//     */
//    Example(int i, int j){
//        System.out.println("constructor with Two parameters");
//    }
//    /* Parameterized constructor with
//     * three integer arguments
//     */
//    Example(int i, int j, int k){
//        System.out.println("constructor with Three parameters");
//    }
//
//    /* Parameterized constructor with
//     * two arguments, int and String
//     */
//    Example(int i, String name){
//        System.out.println("constructor with int and String param");
//    }





