package com.example.placeholder;

public class LinkedGameList
{
    public Node head;

    private class Node
    {
        public Game game;
        public Node next;

        public Node()
        {
            game = null;
            next = null;
        }

        public Node(Game g)
        {
            game = g;
            next = null;
        }
    }

    public LinkedGameList()
    {
        head = null;
        head.next = null;
    }

    public LinkedGameList(Game g)
    {
        head = null;
        head.next = new Node(g);
    }

    public void AddToList(Game g)
    {
        Node n = head;
        while (n.next != null)
        {
            n = n.next;
        }
        n.next = new Node(g);
    }

}

