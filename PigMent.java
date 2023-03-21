import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PigMent {

  /* Global Constants */
  static final int TIC_MIN   = 50;  // Tickrate minimum time (ms)
  static final int TIC_MAX   = 200; // Tickrate maximum time (ms)
  static final int FEED      = 20;  // Mass gained through photosynthesis
  static final int BMR       = 10;  // Mass lost due to basal metabolic rate
  static final int MAX_POP   = 10;  // Maximum number of concurent pigs
  static final int INIT_POP  = 3;   // size of initial pig population
  static final int INIT_MASS = 20;  // starting mass of initial pigs
  
  // TODO: globally accessible variables go here (id, pigPool, openArea)
  static AtomicInteger currentId = new AtomicInteger(0);
  static AtomicInteger numThreadsUsingConsole = new AtomicInteger(0);
  static ExecutorService pigPool = Executors.newFixedThreadPool(MAX_POP);
  static PriorityBlockingQueue<PhotoPig> openArea = new PriorityBlockingQueue<>(MAX_POP,(a,b)->a.mass-b.mass);
  // TODO: explicit locks and conditions also go here (Task2)
  static ReentrantLock lock = new ReentrantLock();
  static Condition condition = lock.newCondition();

  /* Implementing Awesomeness (a.k.a. the pigs) */
  static class PhotoPig implements Runnable {
    
    /* Take this, USA! */
    final int id;

    /* Watch your lines, piggie! */
    int mass;

    /* Sweet dreams (are made of this) */
    void pigSleep() {
      try {
//        System.out.println("Ahhh tired, gonna sleep for time");
        Thread.sleep((long)(new Random().nextInt(TIC_MAX - TIC_MIN + 1)+TIC_MIN));
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }

    /* Ensuring free speech */
    void pigSay(String msg) {
      lock.lock();
      try {
        while (numThreadsUsingConsole.get() > 0) {
          condition.await();
        }
        numThreadsUsingConsole.set(numThreadsUsingConsole.get()+1);
        // display the ID and mass of the speaking pig
        System.out.println("<Pig#"+id+","+mass+"kg>:"+msg);
        numThreadsUsingConsole.set(numThreadsUsingConsole.get()-1);
        condition.signalAll();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      } finally {
        lock.unlock();
      }
    }

    /* Here comes the esoteric stuff */
    boolean eatLight() {
//      registering    //
      openArea.add(this);
      pigSleep();

//        this pig survived the day :)    //
      if(openArea.size() > 0 && openArea.contains(this)){
        openArea.remove(this);

        setMass(mass + FEED);
        setMass(mass - (mass / BMR));

        pigSay("Holy crap, I just ate light!");

//      checking if new piglet can be born  //
        if(mass / BMR > FEED / 2)
          {
            pigSay("This vessel can no longer hold the both of us!");
            setMass(mass/2);
            if(((ThreadPoolExecutor) pigPool).getActiveCount() < MAX_POP)
            {
              pigPool.submit(new Thread(new PhotoPig(mass)));
            }
          }

        return true;
      }


//      could not survive the day :(     //
      return false;
    }

    /* Hey, this ain't vegan! */
    boolean aTerribleThingToDo() {
//      registering    //
      openArea.add(this);

//      looking for a prey    //
      pigSleep();

      PhotoPig prey = openArea.peek();
      if(openArea.size() > 0 && openArea.contains(this))
      {
        openArea.remove(this);
        if(openArea.contains(prey))
        {
          setMass(mass + (prey.mass/2));
          openArea.remove(prey);

          pigSay("Bless me, Father, for I have sinned.");
        }
        return true;
      }

      // pig is dead
      return false;
    }

    void setMass(int mass) {
      this.mass = mass;
    }

    /* Every story has a beginning */
    public PhotoPig(int mass) {
      this.mass = mass;
      this.id   = currentId.get();
      currentId.set(this.id+1);


//      starting speech        //
      pigSay("Beware world, for I'm here now!");
    }

    /* Live your life, piggie! */
    @Override public void run() {
      try
      {
        boolean sunBathing = true;
        boolean hunting = true;
        while(sunBathing && hunting)
        {
          if(openArea.size() > 0  && openArea.peek().mass < this.mass)
          {
            hunting = aTerribleThingToDo();
            if(hunting)
            {
              sunBathing = eatLight();
            }
          }
          else
          {
            sunBathing = eatLight();
          }
        }
        // finishing speech //
        pigSay("I have endured unspeakable horrors. Farewell, world!");
      }catch (Exception e){
        //     For the ones who survived till the end  //
        pigSay("Look on my works, ye Mighty, and despair!");
      }
    }
  }

  /* Running the simulation */
  public static void main(String[] args) {
    for(int i = 0; i<INIT_POP; i++)
    {
      pigPool.submit(new Thread(new PhotoPig(INIT_MASS)));
    }

    try {
      Thread.sleep(6000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }

    pigPool.shutdownNow();
  }
}
