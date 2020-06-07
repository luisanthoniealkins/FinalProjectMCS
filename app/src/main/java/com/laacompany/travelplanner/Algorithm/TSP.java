package com.laacompany.travelplanner.Algorithm;

import java.util.ArrayList;

public class TSP {

    private static int[][] dist, to;
    private static double[][] memo;
    private static int n;
    private static ArrayList<Integer> index;

    public static ArrayList<Integer> simulate(int[][] distI, int nI){
        dist = distI;
        n = nI;
        to = new int[20][1<<n];
        memo = new double[20][1<<n];
        dp(0,1);
        int xi = 0;
        int mask = 1;
        index.clear();
        index.add(xi);
        while(mask != ((1<<n)-1)){
            index.add(xi);
            xi = to[xi][mask];
            mask |= (1<<xi);
        }
        return index;
    }

    private static double dp(int cur, int bitmask){
        if (bitmask == ((1<<n)-1)) return dist[cur][0];
        if (memo[cur][bitmask] != -1) return memo[cur][bitmask];


        double mins = 1e9;
        for(int i=0;i<n;i++){
            if ((bitmask & (1<<i)) == 1) continue;
            double tot = dp(i,bitmask | (1<<i)) + dist[cur][i];
            if (mins > tot){
                mins = tot;
                to[cur][bitmask]=i;
            }
        }
        return memo[cur][bitmask] = mins;
    }

}
