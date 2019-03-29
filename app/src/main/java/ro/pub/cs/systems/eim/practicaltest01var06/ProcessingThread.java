package ro.pub.cs.systems.eim.practicaltest01var06;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;
import java.util.Random;

class ProcessingThread extends Thread{
    private Context context = null;
    private boolean isRunning = true;
    Random random = new Random();
    private int number;

    public ProcessingThread(Context context) {
        this.context = context;

    }

    @Override
    public void run() {
        Log.d(Constants.LOG_TAG, "Thread has started!");
        while(isRunning) {
            sendMessage();
            sleep();
        }


        Log.d(Constants.LOG_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionType);
        number = random.nextInt(9);
        intent.putExtra(Constants.NUMBER_STRING, number);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
