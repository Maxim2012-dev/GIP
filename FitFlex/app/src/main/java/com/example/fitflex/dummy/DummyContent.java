package com.example.fitflex.dummy;

import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    ;
    private static DatabaseReference reference = database.getReference("Oefening");

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    private static int counter = 1;

    static {

        addItem(new DummyItem("test", "Item ", "test", "test"));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String moeilijkheid = ds.child("Moeilijkheid").getValue(String.class);
                    System.out.println(moeilijkheid);
                    String naam = ds.child("Naam").getValue(String.class);
                    addItem(new DummyItem(String.valueOf(counter), naam, moeilijkheid, makeDetails(counter)));
                    counter++;

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);

        builder.append("\nMore details information here.");

        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String naam;
        public final String moeilijkheid;
        public final String details;

        public DummyItem(String id, String naam, String moeilijkheid, String details) {
            this.id = id;
            this.naam = naam;
            this.moeilijkheid = moeilijkheid;
            this.details = details;
        }

        @Override
        public String toString() {
            return naam;
        }
    }
}
