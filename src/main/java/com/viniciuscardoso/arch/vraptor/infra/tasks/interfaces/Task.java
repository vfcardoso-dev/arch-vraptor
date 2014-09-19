/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viniciuscardoso.arch.vraptor.infra.tasks.interfaces;

import org.springframework.scheduling.TaskScheduler;

/**
 *
 * @author Vinícius Cardoso
 */
public interface Task extends Runnable {
    void schedule(TaskScheduler scheduler);
}
