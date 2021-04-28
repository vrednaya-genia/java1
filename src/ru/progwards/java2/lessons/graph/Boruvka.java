package ru.progwards.java2.lessons.graph;

public class Boruvka {
    /*
    3.1 Класс Boruvka

Граф задан в виде ссылочной структуры:

 class Node {
   N info; // информация об узле
   List<Edge<N, E>> in; // массив входящих ребер
   List<Edge<N, E>> out; // массив исходящих ребер
}

class Edge<N, E> {
   E info; // информация о ребре
   Node<N, E> out; // вершина, из которой исходит ребро
   Node<N, E> in; // вершина, в которую можно попасть
   // по этому ребру
   double weight; // стоимость перехода
}

class Graph<N, E> {
   List<Node<N, E>> nodes;
   List<Edge<N, E>> edges;
}
Требуется реализовать метод static List<Edge> minTree(Graph graph), который возвращает минимальное остовное дерево в виде списка дуг графа.
     */
}
