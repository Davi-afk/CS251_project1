/**
 * CS 251: Data Structures and Algorithms
 * Project 1: Part 1
 *
 * TODO: Complete CyclicDoubleQueue.
 *
 * @author ,  Jordan Davis
 * @username , Davi1304
 * @sources , none
 *
 *
 *
 */

@SuppressWarnings("unchecked")
public class CyclicDoubleQueue<Item> {


    // initial capacity of the queue
    private int initialCapacity = 7;

    // queue (array of items)
    private Item[] queue;

    // front of the queue
    private int front;

    // back of the queue
    private int back;

    // keeps track of the size;
    private int size;

    // increase factor for resizing the queue
    private int increaseFactor = 2;

    // decrease factor for resizing the queue
    private double decreaseFactor = 0.50;


    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * initial capacity = 7;
     *
     */
    @SuppressWarnings("unchecked")
    public CyclicDoubleQueue() {
        this.queue = (Item[]) new Object[this.initialCapacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }


    /**
     * Constructor of the class.
     * TODO: complete the default Constructor of the class
     *
     * initial capacity = 7;
     *
     */
    @SuppressWarnings("unchecked")
    public CyclicDoubleQueue(int initialCapacity, int increaseFactor, double decreaseFactor) {
        this.initialCapacity = initialCapacity;
        this.increaseFactor = increaseFactor;
        this.decreaseFactor = decreaseFactor;
        this.queue = (Item[]) new Object[this.initialCapacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }


    public int getFront() {
        return this.front;
    }

    public int getBack() {
        return this.back;
    }

    public int getSize() {
        return this.size;
    }

    public int getQueueLength() {
        return this.queue.length;
    }


    /**
     *
     * TODO: Enqueue the passed item to the front of the queue.
     *
     */
    public void enqueueFront(Item item) {
        if (this.isEmpty())
        {
            this.queue[this.front] = item;
            this.back = this.front + 1;
            this.size = this.size + 1;
        }
        else if (this.front - 1 < 0)
        {
            if (this.queue[this.queue.length - 1] != null )
            {
                Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
                int index = 1;
                temp[0] = item;
                for (int i = this.front; i < this.queue.length; i++)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
                for (int i = this.front - 1; i >= 0; i--)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
                this.queue = temp;
                this.front = 0;
                this.back = index;
                this.size = this.size + 1;
            }
            else
            {
                this.queue[this.queue.length - 1] = item;
                this.front = this.queue.length - 1;
                this.size = this.size + 1;
            }
        }
        else if (this.queue[this.front - 1] != null )
        {
            Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
            int index = 1;
            temp[0] = item;
            for (int i = this.front; i < this.queue.length; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            for (int i = this.front - 1; i >= 0; i--)
            {
                temp[index] = this.queue[i];
                index++;
            }
            this.queue = temp;
            this.front = 0;
            this.back = index;
            this.size = this.size + 1;

        }
        else
        {
            this.queue[this.front - 1] = item;
            this.front = this.front - 1;
            this.size = this.size + 1;
        }


    }


    /**
     *
     * TODO: dequeue the element at the front of the queue
     *
     */
    public Item dequeueFront() throws Exception {
        if (this.isEmpty())
        {
            throw new Exception();
        }
        Item[] temp2 =  (Item[]) new Object[1];
        if ((this.size <= (.25 * this.queue.length)) && ((this.queue.length * this.decreaseFactor) >= initialCapacity))
        {
            Item[] temp =  (Item[]) new Object[(int) (this.queue.length * this.decreaseFactor)];
            int index = 0;
            for (int i = this.front + 1; i < this.queue.length; i++)
            {
                if (this.queue[i] != null)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
            }
            for (int i = this.front - 1; i >= 0; i--)
            {
                if (this.queue[i] != null)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
            }
            temp2[0] = this.queue[this.front];
            this.queue = temp;
            this.front = 0;
            this.back = index;
            this.size = this.size - 1;

        }
        else
        {
            this.size = this.size - 1;
            temp2[0] = this.queue[this.front];
            this.queue[this.front] = null;
            if (this.front + 1 < this.queue.length)
            {
                this.front = this.front + 1;
            }
            else
            {
                this.front = 0;
            }
        }

        return temp2[0];
    }


    /**
     *
     * TODO: Enqueue the passed item to the back of the queue.
     *
     */
    public void enqueueBack(Item item) {
        if (this.isEmpty())
        {
            this.queue[0] = item;
            this.back = 1;
            this.size = this.size + 1;
        }
        else if (this.back + 1 > this.queue.length)
        {
            if (this.queue[0] != null )
            {
                Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
                int index = 0;
                for (int i = this.front; i < this.queue.length; i++)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
                for (int i = this.front - 1; i > 0; i--)
                {
                    temp[index] = this.queue[i];
                    index++;
                }
                this.queue = temp;
                this.front = 0;
                this.back = index;
                this.size = this.size + 1;
            }
            else
            {
                this.queue[0] = item;
                this.back = 1;
                this.size = this.size + 1;
            }
        }
        else if (this.queue[this.back] != null )
        {
            Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
            int index = 0;
            for (int i = this.front; i < this.queue.length; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            for (int i = this.front - 1; i >= 0; i--)
            {
                temp[index] = this.queue[i];
                index++;
            }
            this.queue = temp;
            this.front = 0;
            this.queue[index] = item;
            this.back = index + 1;
            this.size = this.size + 1;

        }
        else
        {
            this.queue[this.back] = item;
            this.size = this.size + 1;
            if ((this.back + 1) >= this.queue.length)
            {
                this.back = 0;
            }
            else
            {
                this.back = this.back + 1;
            }
        }


    }


    /**
     *
     * TODO: dequeue the element at the back of the queue
     *
     */
    public Item dequeueBack() throws Exception {
        if (this.isEmpty())
        {
            throw new Exception();
        }
        int count = 0;
        for (int i = 0; i < this.queue.length; i++)
        {
            if (this.queue[i] != null)
            {
                count++;
            }
        }
        if ((count < (.25 * this.queue.length)) && ((this.queue.length * this.decreaseFactor) > initialCapacity)) {
            Item[] temp = (Item[]) new Object[this.queue.length * this.increaseFactor];
            int index = 0;
            for (int i = this.front; i < this.queue.length; i++) {
                if (this.queue[i] != null) {
                    if (i != this.back) {
                        temp[index] = this.queue[i];
                        index++;
                    }
                }
            }
            for (int i = this.front - 1; i > 0; i--) {
                if (this.queue[i] != null) {
                    if (i != this.back) {
                        temp[index] = this.queue[i];
                        index++;
                    }
                    ;
                }
            }
            this.queue = temp;
            this.front = 0;
            this.back = index;
            this.size = this.size - 1;

        } else {
            this.size = this.size - 1;
            if ((this.back - 1) < 0)
            {
                this.back = this.queue.length;
                this.queue[this.back] = null;
            }
            else
            {
                this.back = this.back - 1;
                this.queue[this.back] = null;
            }
        }

        return null;
    }


    /**
     *
     * TODO: peek the element at the front of the queue
     *
     */
    public Item peekFront() throws Exception {
        if (this.isEmpty())
        {
            throw new Exception();
        }
        return this.queue[this.front];
    }


    /**
     *
     * TODO: peek the element at the back of the queue
     *
     */
    public Item peekBack() throws Exception {
        if (this.isEmpty())
        {
            throw new Exception();
        }
        if (this.back == this.front)
        {
            return this.queue[this.back];
        }
        else if (this.back - 1 < 0)
        {
            return this.queue[this.queue.length - 1];
        }
        else {
            return this.queue[this.back - 1];
        }
    }


    /**
     *
     * TODO: check if the queue is empty
     *
     */
    public boolean isEmpty() {
        return this.size == 0;
    }
    public static void main(String[] args) throws Exception {
        CyclicDoubleQueue<String> temp = new CyclicDoubleQueue<>(4, 2, .75);
        temp.enqueueFront("1");
        temp.enqueueFront("2");
        temp.enqueueFront("3");
        temp.enqueueFront("4");
        temp.enqueueFront("5");
        temp.enqueueFront("6");
        temp.enqueueFront("7");
        temp.enqueueFront("8");
        temp.enqueueFront("9");
        temp.enqueueFront("10");
        temp.enqueueFront("11");
        temp.enqueueFront("12");
        temp.enqueueFront("13");
        temp.enqueueFront("14");
        temp.enqueueFront("15");
        System.out.println(temp.peekFront());
        System.out.println(temp.peekBack());
        System.out.println(temp.getBack());
        System.out.println(temp.dequeueFront());
        System.out.println(temp.dequeueFront());
        System.out.println(temp.dequeueFront());
        System.out.println(temp.dequeueFront());
        System.out.println(temp.dequeueFront());
        System.out.println(temp.getQueueLength());



    }



}
