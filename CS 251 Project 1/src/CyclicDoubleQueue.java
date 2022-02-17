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
        if (this.isEmpty())//check if queue is empty
        {
            this.front = 0;
            this.queue[this.front] = item;
            this.back = 1;
            this.size = 1;
        }
        else if (this.size == this.queue.length)//check if queue is full
        {
            Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
            temp[0] = item;
            int index = 1;
            for (int i = this.front; i < this.queue.length; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            for (int i = 0; i < this.front; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            this.queue = temp;
            this.front = 0;
            this.back = index;
            this.size = index;
        }
        else if ((this.front - 1) < 0)//check if index will have to shift to back
        {
                    this.queue[this.queue.length - 1] = item;
                    this.front = this.queue.length - 1;
                    this.size = this.size + 1;
        }
        else // if queue is not full and index doesnt need to shift
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
        if ((this.size <= (.25 * this.queue.length)) && ((this.queue.length * this.decreaseFactor) >= initialCapacity)) // check if array needs to be shrunk
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
            for (int i = 0; i < this.front; i++)
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
            this.size = index;

        }
        else // check if index is at end of queue if so shift to zero if not shift right one
        {
            this.size = this.size - 1;
            temp2[0] = this.queue[this.front];
            this.queue[this.front] = null;
            if ((this.front + 1) < this.queue.length)
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
        if (this.isEmpty()) // check if queue is empty
        {

            this.front = 0;
            this.queue[this.front] = item;
            this.back = 1;
            this.size = 1;
        }
        else if (this.size == this.queue.length) //check if queue is full
        {
            Item[] temp =  (Item[]) new Object[this.queue.length * this.increaseFactor];
            int index = 0;
            for (int i = this.front; i < this.queue.length; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            for (int i = 0; i < this.front; i++)
            {
                temp[index] = this.queue[i];
                index++;
            }
            this.queue = temp;
            this.front = 0;
            this.queue[index] = item;
            this.back = index + 1;
            this.size = index + 1;
        }
        else //queue is not full
            {
                this.queue[this.back] = item;
                this.size = this.size + 1;
                if ((this.back + 1) >= this.queue.length)//check if back index reached the end of the array
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
        Item[] temp2 =  (Item[]) new Object[1];
        if ((this.size <= (.25 * this.queue.length)) && ((this.queue.length * this.decreaseFactor) >= initialCapacity)) {
            Item[] temp =  (Item[]) new Object[(int) (this.queue.length * this.decreaseFactor)];
            int index = 0;
            for (int i = this.front; i < this.queue.length; i++) {
                if (this.queue[i] != null)
                {
                        temp[index] = this.queue[i];
                        index++;
                }
            }
            for (int i = 0; i < this.front; i++) {
                if (this.queue[i] != null) {
                        temp[index] = this.queue[i];
                        index++;
                }
            }
            temp2[0] = temp[index - 1];
            this.queue = temp;
            this.queue[index - 1] = null;
            this.front = 0;
            this.back = index - 1;
            this.size = index - 1;

        } else //if back is normal
            {
            this.size = this.size - 1;
            if ((this.back - 1) < 0)//if back value is at end of array
            {
                this.back = this.queue.length - 1;
                temp2[0] = this.queue[this.back];
                this.queue[this.back] = null;
            }
            else
            {
                this.back = this.back - 1;
                temp2[0] = this.queue[this.back];
                this.queue[this.back] = null;
            }
        }
        return temp2[0];
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
        if ((this.back - 1) < 0)//check if back is last index in queue
        {
            return this.queue[this.queue.length - 1];
        }
        else //back in normal
            {
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
}
