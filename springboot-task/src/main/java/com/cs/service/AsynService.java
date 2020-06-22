package com.cs.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author: szh
 * @since:
 */


@Service
public class AsynService {

    //tell spring this is a async method
    @Async
    public void hello(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("operating data....");
    }
}
