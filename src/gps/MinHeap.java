/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps;

/**
 *
 * @author Mohanad
 */
public class MinHeap { 
    private Edge[] Heap; 
    private int size; 
    private int maxsize; 
  
    private static final int FRONT = 1; 
  
    public MinHeap(int maxsize) 
    { 
        this.maxsize = maxsize; 
        this.size = 0; 
        Heap = new Edge[this.maxsize + 1];
        for(int i = 0; i < Heap.length; i++){
            Heap[i] = new Edge();
        }
        Heap[0].distance = Double.MIN_VALUE; 
    } 
  
    private int parent(int pos) 
    { 
        return pos / 2; 
    } 
    
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    } 
    
    private int rightChild(int pos) 
    { 
        return (2 * pos) + 1; 
    } 
    
    private boolean isLeaf(int pos) 
    { 
        if (pos >= (size / 2) && pos <= size) { 
            return true; 
        } 
        return false; 
    } 
    
    private void swap(int fpos, int spos) 
    { 
        Edge tmp; 
        tmp = Heap[fpos]; 
        Heap[fpos] = Heap[spos]; 
        Heap[spos] = tmp; 
    } 
    
    private void minHeapify(int pos) 
    { 
  
        // If the node is a non-leaf node and greater 
        // than any of its child 
        if (!isLeaf(pos)) { 
            if (Heap[pos].compareTo(Heap[leftChild(pos)]) == 1 
                || Heap[pos].compareTo(Heap[rightChild(pos)]) == 1) { 
  
                // Swap with the left child and heapify 
                // the left child 
                if (Heap[leftChild(pos)].compareTo(Heap[rightChild(pos)]) == -1) { 
                    swap(pos, leftChild(pos)); 
                    minHeapify(leftChild(pos)); 
                } 
  
                // Swap with the right child and heapify 
                // the right child 
                else { 
                    swap(pos, rightChild(pos)); 
                    minHeapify(rightChild(pos)); 
                } 
            } 
        } 
    } 
    
    public void insert(Edge element) 
    { 
        if (size >= maxsize) { 
            return; 
        } 
        Heap[++size] = element; 
        int current = size; 
  
        while (Heap[current].compareTo(Heap[parent(current)]) == -1) { 
            swap(current, parent(current)); 
            current = parent(current); 
        } 
    } 
    
    public void print() 
    { 
        for (int i = 1; i <= size / 2; i++) { 
            System.out.print(" PARENT : " + Heap[i] 
                             + " LEFT CHILD : " + Heap[2 * i] 
                             + " RIGHT CHILD :" + Heap[2 * i + 1]); 
            System.out.println(); 
        } 
    } 
    
    public void minHeap() 
    { 
        for (int pos = (size / 2); pos >= 1; pos--) { 
            minHeapify(pos); 
        } 
    } 
    
    public Edge remove() 
    { 
        Edge popped = Heap[FRONT]; 
        Heap[FRONT] = Heap[size--]; 
        if(size == 0){
            return popped;
        }
        minHeapify(FRONT); 
        return popped; 
    } 
    
    public Edge peek(){
        return Heap[FRONT];
    }
    
    public int size(){
        return size;
    }
} 
