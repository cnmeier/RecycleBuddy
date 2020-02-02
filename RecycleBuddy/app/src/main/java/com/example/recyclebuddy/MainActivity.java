package com.example.recyclebuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.auth.providers.userpassword.UserPasswordCredential;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ChangeEventListener;
import com.mongodb.stitch.core.services.mongodb.remote.sync.DefaultSyncConflictResolvers;
import com.mongodb.stitch.core.services.mongodb.remote.sync.ErrorListener;
//import com.mongodb.stitch.core.services.mongodb.remote.sync.internal.ChangeEvent;

import org.bson.BsonValue;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

 /**
    final StitchAppClient client =
            Stitch.initializeDefaultAppClient("recyclebuddy-opnht");

    final RemoteMongoClient mongoClient =
            client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

    final RemoteMongoCollection<Document> coll =
            mongoClient.getDatabase("Items").getCollection("DBItems");
*/

    StitchAppClient client;

    RemoteMongoClient mongoClient;

    RemoteMongoCollection<Document> coll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            client =
                    Stitch.initializeDefaultAppClient("recyclebuddy-opnht");

            mongoClient =
                    client.getServiceClient(RemoteMongoClient.factory, "mongodb-atlas");

            coll =
                    mongoClient.getDatabase("Items").getCollection("DBItems");
        }catch(Exception e){
            Log.w("MainActivity", e);
        }

        EditText email = (EditText) findViewById(R.id.emailID);
        EditText password = (EditText) findViewById(R.id.passwordID);
        Button login = (Button) findViewById(R.id.loginID);

        //connectMongo();
        login.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String emailText = email.getText().toString();
                        Log.w("SignupActivity", emailText);
                        String passwordText = password.getText().toString();
                        Log.w("SignupActivity", passwordText);
                        loginUser(emailText, passwordText);

                    }
                });

    }

    public void loginUser(String email, String password) {
        UserPasswordCredential credential = new UserPasswordCredential(email, password);
        Stitch.getDefaultAppClient().getAuth().loginWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<StitchUser>() {
                                           @Override
                                           public void onComplete(@NonNull final Task<StitchUser> task) {
                                               if (task.isSuccessful()) {
                                                   Log.d("MainActivity", "Successfully logged in as user " + task.getResult().getId());
                                                    gosearch(null);

                                               } else {
                                                   Log.e("MainActivity", "Error logging in with email/password auth:", task.getException());
                                                    showDialog();
                                               }
                                           }
                                       }
                );

    }



    public void connectMongo() {
        Log.w("MainActivity", client.toString());
        Log.w("MainActivity", mongoClient.toString());
        Log.w("MainActivity", coll.toString());

        client.getAuth().loginWithCredential(new AnonymousCredential()).continueWithTask(
                //getApplicationContext().unbindService();

                new Continuation<StitchUser, Task<RemoteUpdateResult>>() {

                    @Override
                    public Task<RemoteUpdateResult> then(@NonNull Task<StitchUser> task) throws Exception {
                        if (!task.isSuccessful()) {
                            Log.e("STITCH", "Login failed!");
                            throw task.getException();
                        }

                        final Document updateDoc = new Document(
                                "owner_id",
                                task.getResult().getId()

                        );

                        updateDoc.put("number", 42);
                        return coll.updateOne(
                                null, updateDoc, new RemoteUpdateOptions().upsert(true)
                        );
                    }
                }
        ).continueWithTask(new Continuation<RemoteUpdateResult, Task<List<Document>>>() {
            @Override
            public Task<List<Document>> then(@NonNull Task<RemoteUpdateResult> task) throws Exception {
                if (!task.isSuccessful()) {
                    Log.e("STITCH", "Update failed!");
                    throw task.getException();
                }
                List<Document> docs = new ArrayList<>();
                return coll
                        .find(new Document("owner_id", client.getAuth().getUser().getId()))
                        .limit(100)
                        .into(docs);
            }
        }).addOnCompleteListener(new OnCompleteListener<List<Document>>() {
            @Override
            public void onComplete(@NonNull Task<List<Document>> task) {
                if (task.isSuccessful()) {
                    StitchUser currUser = client.getAuth().getUser();
                    Log.d("STITCH", "Found docs: " + task.getResult().toString());
                    return;
                }
                Log.e("STITCH", "Error: " + task.getException().toString());
                task.getException().printStackTrace();
            }
        });
    }

/**
    public void connectMongo() {
        //RemoteMongoCollection _remoteCollection;


        // Create the StitchAppClient
        final StitchAppClient client = Stitch.initializeAppClient("recyclebuddy-opnht");
        Log.w("MainActivity", client.toString());


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
                                .getDatabase("Users").getCollection("DBUsers");
                        _remoteCollection.sync().configure(
                                DefaultSyncConflictResolvers.remoteWins(),
                                new MyUpdateListener(),
                                new MyErrorListener());
                    }
                });
    }
*/


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

/**
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
 */

    public void showDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Incorrect password!");
        alert.show();
    }

}
