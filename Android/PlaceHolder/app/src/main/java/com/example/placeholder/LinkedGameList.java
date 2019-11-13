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
        head = new Node();
        head.next = new Node();
    }

    public LinkedGameList(Game g)
    {
        head = null;
        head.next = new Node(g);
    }

    public void AddToList(Game g)
    {
        Node n = head;
        while (n.next.game != null)
        {
            n = n.next;
        }
        n.next = new Node(g);
    }

    public Game getGame()
    {
        if(head.next == null)
        {
            return null;
        }
        return head.next.game;
    }

}

