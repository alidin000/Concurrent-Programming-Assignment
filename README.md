# Concurrent-Programming-Assignment

<hr class="solid" style="border-top: 5px solid grey;" />
<h1 style="text-align: center;">General Information</h1>
<hr class="solid" style="border-top: 5px solid grey;" />

<h4>Files</h4>
<p style="text-align: justify; color:red">skeleton + example output placeholder</p>

<hr class="solid" style="border-top: 1px solid grey;" />
<h4>Tasks and assessment</h4>
<p style="text-align: justify;">The assignment consists of two interrelated tasks. The second task is an augmentation of the first one, so you have to solve Task1 before advancing to Task2.</p>
<p style="text-align: justify;">Each task has smaller tasks, so by skipping certain parts (or creating a simplified version of them) your program might still work. You can get points for a partially solved task. The prerequisite for a successful submission is that your program works, in other words it can be compiled and it runs without error or crashing.</p>
<p style="text-align: justify;">By solving another task incorrectly, you can mess up your existing, partially finished, but functional program. If you already have a working program, you are advised to create the second task as a new file.</p>


<hr class="solid" style="border-top: 1px solid grey;" />
<h4>Tools</h4>
<p style="text-align: justify;"> You do not need to start from scratch. Use the <code>PigMent.java</code> file as a starting point.</p>

<br>

<hr class="solid" style="border-top: 5px solid green;" />
<h1 style="text-align: center;">Task1 (9p)</h1>
<h3 style="text-align: center;">Executor, Runnable, synchronized, AtomicInteger, wait-notify</h3>
<h4 style="text-align: center; color: gray"><i>Difficulty: I can do this!</i></h4>
<hr class="solid" style="border-top: 5px solid green;" />

<h4>Story Time</h4>
<p style="text-align: justify;">On a planet called <i>PigMent</i>, the living world hid underground from the blazing sun. An exception to this is a strange specie of pigs that has adapted to the cruel environment. As a result of a strange mutation, a purple pigment appeared in their skin, with the help of which they reflect most of the harmful ultraviolet radiation. From the absorbed light, they develop energy through photosynthesis. Pigs are gender-neutral, so once they reach a certain size, they reproduce by dividing (similarly to bacteria). Each pig has an independent will and decision-making ability, so you have to treat each pig separately and in parallel with the other pigs during the program.</p>

<hr class="solid" style="border-top: 1px solid green;" />
<h4>pigSleep - 1 pont</h4>
<p style="text-align: justify;">Not everything happens immediately. Big things have to wait.. or sleep..</p>
<p style="text-align: justify;">It is no different here, even in the society of pigs, sometimes there's not much to do. Write a <code>pigSleep()</code> method that blocks the calling thread for a certain time between <code>TICK_MIN</code> and <code>TICK_MAX</code> milliseconds.</p>

<hr class="solid" style="border-top: 1px solid green;" />
<h4>PhotoPig - 1 pont</h4>
<p style="text-align: justify;">The pig breed is described by a class named <code>PhotoPig</code>. In order to be able to track individual pigs, each copy has a unique identifier (<code>id</code>). Furthermore, their weight can be set parametrically at birth (<code>mass</code>), which is also stored by the class.</p>
<p style="text-align: justify;">Make sure that the initial mass can be set through a constructor argument. With the help of a globally accessible <code>AtomicInteger</code> make sure each pig receives a unique id. Start the global counter with zero and increment its value by one every time a new pig is born.</p>

<hr class="solid" style="border-top: 1px solid green;" />
<h4>pigSay - 1 pont</h4>
<p style="text-align: justify;"> Although the piglets tolerate the weather well, their brains are slightly cooked in the high heat. Because of this, they tend to shout nonsense on a regular basis.</p>
<p style="text-align: justify;">Give a voice to these wonderful creatures of God by implementing the <code>pigSay(String msg)</code> method. The method should write to the console, and also display the ID and mass of the speaking pig.</p>
<p style="text-align: justify;">Only one pig can speak at a time! Otherwise it would be just noise.</p>

<hr class="solid" style="border-top: 1px solid green;" />
<h4>eatLight() és run() - 4 pont</h4>
<p style="text-align: justify;">Pigs lose weight continuously during their metabolism. Catabolic processes (losing weight) are compensated by anabolic processes (gaining weight). However, weightgain requires a caloric surplus. This is why people tend to eat. Here, however, we have photosynthesizing pigs, so it happens a little differently with them. They lay down and rest to absorb sunlight instead of eating. When they've sucked themselves up, they head home. On the way home, the anabolic processes take place within their bodies. As a result they get more muscular and more fat, so they will be bigger than before. When they reach a critical mass, they split into two. The original pig remains alive, but will carry half of its original mass. The other half becomes self-aware and separates from the mother's body.</p>
<p style="text-align: justify;">Make the <code>PhotoPig</code> class to be a <code>Runnable</code> implementation, so individual pigs can be thrown into an <code>ExecutorService</code> object (<code>pigPool</code>). Make sure that each pig can live in parallel with the other pigs!</p>
<p style="text-align: justify;">A pig's life begins with the <code>"Beware world, for I'm here now!"</code> cry and ends with the <code>"I have endured unspeakable horrors. Farewell, world!"</code> sigh. Between the two, they lie down to bask in the sun again and again. The sunbathing and its consequence (division) should be implemented in the <code>eatLight()</code> method. Its return value indicates whether the pig has met its fate while sunbathing. The method should return <code>true</code> if the pig is still alive. The method should be called again and again until a <code>false</code> value is returned. In the first task, the animals cannot die yet, so an endless cycle takes place here. For the first task, this is completely normal behaviour!</p>
<p style="text-align: justify;">The <code>eatLight()</code> method treats both anabolic (mass-increasing) and catabolic (mass-reducing) processes. After the pig had busked in the sun, we can evaluate the metabolic outcome. The mass obtained from sunbathing is determined by the capacity of the internal organs, so it is independent of the mass of the pig. After photosyntesis <code>FEED</code> amount of mass is always added. The weight lost depends on the weight of the pig, make the pig lose <code>mass / BMR</code> amount of weight. Note that heavier pigs have a worse ratio of gained and lost weight. If this ratio is less than 50%, then the pig should procriate! You can use the following formula: <code>mass / BMR > FEED / 2</code>, if this is evaluated to true, then a new pig is supposed to be born at the end of <code>eatLight()</code>. The original pig remains alive at half the mass. With the other half create a new pig and bring it to life by throwing it into the <code>ExecutorService pigPool</code> object. If we have reached <code>MAX_POP</code> number of births, then do not place the newborn into the executor object!</p>
<p style="text-align: justify;">After eating light, the pigs squeal in joy <code>"Holy crap, I just ate light!"</code>, and when splitting, they squeal <code>"This vessel can no longer hold the both of us!"</code>.</p>

<hr class="solid" style="border-top: 1px solid green;" />
<h4>main() - 2 pont</h4>
<p style="text-align: justify;">At the beginning of the simulation, create <code>INIT_POP</code> number of pigs. Set the initial mass of each pig to <code>INIT_MASS</code>.</p>
<p style="text-align: justify;">If the global pig counter has reached the <code>MAX_POP</code> value, the newly born pig should send a signal, causing the simulation to end. In theory, the simulation can run indefinitely, but in practice we want to interrupt it after a certain number of pig births. Since we are interrupting the simulation, rather than waiting for it to finish, there is no need to fiddle with a "nice" shutdown. It's perfectly fine if you use <code>pigPool.shutdownNow()</code> to immediately terminate all running threads.</p>

<hr class="solid" style="border-top: 5px solid orange;" />
<h1 style="text-align: center;">Task2 (6p)</h1>
<h3 style="text-align: center;">BlockingQueue, ReentrantLock, Condition</h3>
<h4 style="text-align: center;; color: gray"><i>Difficulty: I mean.. I like a challange, right? Right?!</i></h4>
<hr class="solid" style="border-top: 5px solid orange;" />

<h4>Story Time</h4>
<p style="text-align: justify;">The pigs have become so crazy from the gamma radiation caused by the many solar flares that they no longer despise cannibalism. According to them, this is just a more efficient and faster form of getting their daily nutrition. Pigs now come out of their hiding places not only to sunbathe, but also to hunt.</p>
<p style="text-align: justify;">First, they walk around busy places looking for prey. If they find a pig no bigger than them, they look at it as food. While watching, a hunter may fell prey to another hunter. The hunter hides after findind a suitable target. After hiding the other hungry pigs can no longer see the hunter.</p>
<p style="text-align: justify;">If he did not find prey, or while he was hiding, someone else got to his prey, or the prey got away (e.g. the prey is also a hunter who went hiding or just simply went home), then the hunt is unsuccessful. In other cases, the hunt is successful and the pig will be enriched with half the weight of the prey. The prey is killed in this case.</p>
<p style="text-align: justify;"></p>Regardless of success, the hunt is exhausting, so the pigs lie down to rest for the day before heading home. They will then become visible to other hunters again. In return, they get the extra mass for photosynthesis. If they are still alive by the time they wake up, then they survived the the current trip. On the way home, the weight loss and division resulting from metabolism take place in a similar way as in the first task.</p>

<hr class="solid" style="border-top: 1px solid orange;" />
<h4>openArea és aTerribleThingToDO() - 4 pont</h4>
<p style="text-align: justify;">Use a <code>PriorityBlockingQueue</code> (<code>openArea</code>) to enable the pigs to register when they are visible and when they are not visible to the hunters. Help with instantiation: <code>new PriorityBlockingQueue<>(MAX_POP, (a,b) -> a.mass - b.mass);</code></p>
<p style="text-align: justify;">During hunting the hunter spots a pig with the help of the queue. After the prey is identified, the hunter tries to hide. This takes <code>pigSleep()</code> amount of time and the hunter can be seen by other predators during this action. To make the pig visible to others it has to register (insert) itself with the <code>openArea</code> object and deregister (remove) itself afterwards. If the <code>openArea.remove(this)</code> command failes, it means the hunter was hunted down by another hunter and is dead now. If the hunter is still alive, but the prey could not be removed from the <code>openArea</code> object</p>, then the hunt was a failure. E.g. the hunt failes if the prey hides (the prey removes itself from the queue) or was killed by another hunter in the meantime.</p>
<p style="text-align: justify;">Implement the hunting mechanism inside the <code>aTerribleThingToDO()</code> method. The <code>eatLight()</code> method should register and deregister similary to the hunt method (pigs are visibles during sunbathing). If the pig cannot remove itself from the queue, then the pig is dead and the method returns false. The <code>run()</code> method now calls both the hunt and sunbathe method in a loop, until one of them returns false.</p>
<p style="text-align: justify;">After a successful hunt the pig writes <code>"Bless me, Father, for I have sinned."</code> to the console.</p>

<hr class="solid" style="border-top: 1px solid orange;" />
<h4>Improved parallelisation - 2 pont</h4>
<p style="text-align: justify;"> Use the registration / deregistration mechanism as a form of mutual exclusion. If used properly, multiple synchronizations can be avoided.</p>
<p style="text-align: justify;">Use explicit locks (<code>ReentrantLock</code>)! Use <code>Condition</code> instead of wait-notify</p>
<p style="text-align: justify;">Use the interrupt from <code>pigPool.shutDownNow()</code> to initiate the end of the program. Surviving pigs get overconfident and yell <code>"Look on my works, ye Mighty, and despair!"</code>.</p>
