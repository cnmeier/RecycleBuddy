package com.example.recyclebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ChangeEventListener;
import com.mongodb.stitch.core.services.mongodb.remote.sync.DefaultSyncConflictResolvers;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ErrorListener;
import com.mongodb.stitch.core.services.mongodb.remote.sync.internal.ChangeEvent;

import org.bson.BsonValue;
import org.bson.Document;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

    RemoteMongoCollection _remoteCollection;



    public void connectMongo() {
        //RemoteMongoCollection _remoteCollection;


        // Create the StitchAppClient
        final StitchAppClient client = Stitch.initializeAppClient("<APP_ID>");


        // Log-in using an Anonymous authentication provider from Stitch
        client.getAuth().loginWithCredential(new AnonymousCredential())
                .addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                    @Override
                    public void onComplete(@NonNull Task<StitchUser> task) {
                        // Get a remote client
                        final RemoteMongoClient remoteMongoClient =
                                client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

                        // Set up the atlas collection
                         _remoteCollection = remoteMongoClient
                                .getDatabase("my_db").getCollection("my_collection");
                        _remoteCollection.sync().configure(
                                DefaultSyncConflictResolvers.remoteWins(),
                                new MyUpdateListener(),
                                new MyErrorListener());
                    }
                });
    }



    /**
     * Sets up the screen that follows after the user clicks the signup button
     * on the first screen
     *
     * @param v
     *            the screen view
     */
    public void gosignup(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        this.startActivity(intent);
    }

    /**
     * Sets up the screen that follows after the user clicks the login button
     * on the first screen and their username/password combination were correct
     *
     * @param v
     *            the screen view
     */
    public void gosearch(View v) {
        Intent intent = new Intent(this, SearchActivity.class);
        this.startActivity(intent);
    }


    private class MyErrorListener implements ErrorListener {
        @Override
        public void onError(BsonValue documentId, Exception error) {
            Log.e("Stitch", error.getLocalizedMessage());
            Set<BsonValue> docsThatNeedToBeFixed = _remoteCollection.sync().getPausedDocumentIds();
            for (BsonValue doc_id : docsThatNeedToBeFixed) {
                // Add your logic to inform the user.
                // When errors have been resolved, call
                _remoteCollection.sync().resumeSyncForDocument(doc_id);
            }
            // refresh the app view, etc.
        }
    }

    private class MyUpdateListener implements ChangeEventListener<Document> {
        @Override
        public void onEvent(final BsonValue documentId, final ChangeEvent<Document> event) {
            if (!event.hasUncommittedWrites()) {
                // Custom actions can go here
            }
            // refresh the app view, etc.
        }
    }



}
