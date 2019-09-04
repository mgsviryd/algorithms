package com.test.structureOfData.lafore.code.tree._234Tree;

class DataItem
{
    public long dData; // Один объект данных
    //--------------------------------------------------------------
    public DataItem(long dd) // Конструктор
    { dData = dd; }
    //--------------------------------------------------------------
    public void displayItem() // Вывод элемента в формате "/27"
    { System.out.print("/"+dData); }
//--------------------------------------------------------------
} // Конец класса DataItem
