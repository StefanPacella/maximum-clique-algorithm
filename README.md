This algorithm finds the maximum clique in a graph and is inspired by the following document: 

https://www.researchgate.net/publication/278716368_A_review_on_algorithms_for_maximum_clique_problems.

The project has not been completed, and in the near future, I will attempt to improve it.

At this address, you can find some DIMACS graphs.

https://networkrepository.com/dimacs.php

To find a clique in a graph, simply execute the following command in the terminal:

```
java -jar solver_v1.jar brock200-2.mtx
```

I ran some tests with the CPU ryzen 5600g and linux kernel 6.8.0:

| Graph  | Seconds |
| ------------- | ------------- |
| brock200-1.mtx  | 4.065  |
| brock200-2.mtx  | 0.212  |
| brock200-3.mtx  | 0.424  |
| brock200-4.mtx  | 0.791  |
| c-fat200-1.mtx  | 0.049  |
| c-fat200-2.mtx  | 0.086  |
| c-fat200-5.mtx  |  0.209 |
| c-fat500-1.mtx  | 0.083  |
| c-fat500-2.mtx  | 0.111  |
| c-fat500-5.mtx  | 0.199  |
| c-fat500-10.mtx  | 0.561 |
| DSJC500-5.mtx  | 6.038  |
| p-hat300-3.mtx  | 31.526  |
| san200-0-7-1.mtx  | 0.285  |
| san200-0-9-1.mtx  | 0.379  |
| san200-0-9-2.mtx  | 22.949  |
| san200-0-9-3.mtx  | 209.892 |

The table lists some graphs and the time in seconds it takes for the solver to find the solution.



