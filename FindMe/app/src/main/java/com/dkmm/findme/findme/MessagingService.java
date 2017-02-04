package com.dkmm.findme.findme;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MessagingService extends Service {
    public MessagingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
