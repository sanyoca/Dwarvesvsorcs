package com.example.sanya.dwarvesvsorcs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    //
// Define global variables for storing the resources and the honor points, Buttons and other views
//
    int dwarvenResources = 10000;
    int orcishResources = 10000;
    int dwarvenHonor = 0;
    int orcishHonor = 0;
    int activeGroup = 0;

    Button dwarf0, dwarf1, dwarf2, dwarf3;
    Button orcish0, orcish1, orcish2, orcish3;


    TextView dRes, oRes, dHon, oHon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
// Initializing the globals
//
        dwarf0 = (Button) findViewById(R.id.dwarf0);
        dwarf1 = (Button) findViewById(R.id.dwarf1);
        dwarf2 = (Button) findViewById(R.id.dwarf2);
        dwarf3 = (Button) findViewById(R.id.dwarf3);

        orcish0 = (Button) findViewById(R.id.orcish0);
        orcish1 = (Button) findViewById(R.id.orcish1);
        orcish2 = (Button) findViewById(R.id.orcish2);
        orcish3 = (Button) findViewById(R.id.orcish3);

        dRes = (TextView) findViewById(R.id.dwarvenResources);
        oRes = (TextView) findViewById(R.id.orcishResources);

        dHon = (TextView) findViewById(R.id.dwarvenHonor);
        oHon = (TextView) findViewById(R.id.orcishHonor);

// which side starts? The other side's buttons will be disabled
        int start = (int) Math.random() * 100;
        if (start <= 50) {
            orcish0.setEnabled(!orcish0.isEnabled());
            orcish1.setEnabled(!orcish1.isEnabled());
            orcish2.setEnabled(!orcish2.isEnabled());
            orcish3.setEnabled(!orcish3.isEnabled());
            activeGroup = 0; // the dwarves begin
        } else {
            dwarf0.setEnabled(!dwarf0.isEnabled());
            dwarf1.setEnabled(!dwarf1.isEnabled());
            dwarf2.setEnabled(!dwarf2.isEnabled());
            dwarf3.setEnabled(!dwarf3.isEnabled());
            activeGroup = 1; // the orcs begin
        }
    }

    //
// This method is called when a dwarven button is clicked
//
    public void dwarven(View view) {

//getting the multiplyer from the tag

        int multiply = Integer.valueOf(view.getTag().toString());

// generating the the attack and defense rolls
        int attackRoll = (int) ((Math.random() * 100) + 1);

        int defenseRoll = (int) ((Math.random() * 100) + 1);
        String report = "";

// if the dwarves (the attackers) won
        if (attackRoll >= defenseRoll) {
            report = getString(R.string.dwarfwin);
            dwarvenHonor = dwarvenHonor + multiply;

            dwarvenResources = dwarvenResources - (defenseRoll * 10 * multiply);
            orcishResources = orcishResources - ((attackRoll - defenseRoll) * 10 * multiply);
        } else {

// if they lost
            report = getString(R.string.dwarflost);

            dwarvenResources = dwarvenResources - (attackRoll * 10 * multiply);
            orcishResources = orcishResources - ((defenseRoll - attackRoll) * 10 * multiply);
        }

// generating report text plus info
// report = report + "/" + getString(R.string.attack) + valueOf(attackRoll) + ", " + getString(R.string.defense) + valueOf(defenseRoll) + ", " + getString(R.string.multiply) + valueOf(multiply) + "/";

// disable the dwarves button - it's the orcs' turn, so activate their buttons
        switchSides();

// displaying the report
        displayReport(report);

    }

    //
// This method is called when an orcish button is clicked
//
    public void orcish(View view) {

        int multiply = Integer.valueOf(view.getTag().toString());

// generating the the attack and defense rolls
        int attackRoll = (int) ((Math.random() * 100) + 1);
        int defenseRoll = (int) ((Math.random() * 100) + 1);
        String report = "";

// if the orcs (the attackers) won
        if (attackRoll >= defenseRoll) {
            report = getString(R.string.orcwin);
            orcishHonor = orcishHonor + multiply;

            dwarvenResources = dwarvenResources - (defenseRoll * 10 * multiply);
            orcishResources = orcishResources - ((attackRoll - defenseRoll) * 10 * multiply);
        } else {

// if they lost
            report = getString(R.string.orclost);

            dwarvenResources = dwarvenResources - (attackRoll * 10 * multiply);
            orcishResources = orcishResources - ((defenseRoll - attackRoll) * 10 * multiply);
        }

// generating report text plus info
// report = report + "/" + getString(R.string.attack) + valueOf(attackRoll) + ", " + getString(R.string.defense) + valueOf(defenseRoll) + ", " + getString(R.string.multiply) + valueOf(multiply) + "/";

// disable the orcs button - it's the dwarves' turn, so activate their buttons
        switchSides();

// displaying report
        displayReport(report);

    }

    //
// Method for resource collection for the dwarves
//
    public void dwarvenCollect(View view) {

// generating a random number of resources (500-1000)
        int collectedResources = 500 + (int) (Math.random() * 500) + 1;

// adding it to the current resource value
        dwarvenResources = dwarvenResources + collectedResources;

// disable the dwarf button - it's the orcs' turn, so activate their buttons
        switchSides();

// displaying it, using report
        String report = getString(R.string.dwarfharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        displayReport(report);
    }

//
// Method for resource collection for the orcs
//

    public void orcishCollect(View view) {

// generating a random number of resources (500-1000)
        int collectedResources = 500 + (int) (Math.random() * 500) + 1;

// adding it to the current resource value
        orcishResources = orcishResources + collectedResources;

// disable the orcs button - it's the dwarves' turn, so activate their buttons
        switchSides();

// displaying it, using report
        String report = getString(R.string.orcharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        displayReport(report);
    }

    //
// This method is for resetting purposes, stating that the orcs and dwarves came to a peace agreement (lol)
//
    public void peaceTreaty(View view) {

// setting the variables to their start state
        dwarvenResources = 10000;
        orcishResources = 10000;
        dwarvenHonor = 0;
        orcishHonor = 0;

// getting the resource TextViews and set their text to the variable value
        dRes.setText(valueOf(dwarvenResources));
        oRes.setText(valueOf(orcishResources));

// doing the same for the honor points
        oHon.setText(valueOf(orcishHonor));
        dHon.setText(valueOf(dwarvenHonor));
        displayReport(getString(R.string.atpeace));

// enable all buttons
        orcish1.setEnabled(true);
        orcish2.setEnabled(true);
        orcish3.setEnabled(true);
        orcish0.setEnabled(true);

        dwarf0.setEnabled(true);
        dwarf1.setEnabled(true);
        dwarf2.setEnabled(true);
        dwarf3.setEnabled(true);

        TextView dWon = (TextView) findViewById(R.id.dwarvesWon);
        dWon.setVisibility(GONE);

        TextView oWon = (TextView) findViewById(R.id.orcsWon);
        oWon.setVisibility(GONE);

        TextView rep = (TextView) findViewById(R.id.report);
        rep.setVisibility(VISIBLE);

// which side starts next time? The other side's buttons will be disabled
        int start = (int) Math.random() * 100;
        if (start <= 50) {
            orcish0.setEnabled(!orcish0.isEnabled());
            orcish1.setEnabled(!orcish1.isEnabled());
            orcish2.setEnabled(!orcish2.isEnabled());
            orcish3.setEnabled(!orcish3.isEnabled());
            activeGroup = 0; // the dwarves begin
        } else {
            dwarf0.setEnabled(!dwarf0.isEnabled());
            dwarf1.setEnabled(!dwarf1.isEnabled());
            dwarf2.setEnabled(!dwarf2.isEnabled());
            dwarf3.setEnabled(!dwarf3.isEnabled());
            activeGroup = 1; // the orcs begin
        }
    }

    //
// This method displays reports
//
    private void displayReport(String r) {
// displaying the resource changes on both sides
        dRes.setText(valueOf(dwarvenResources));
        oRes.setText(valueOf(orcishResources));

// displaying the honor points change on both sides
        dHon.setText(valueOf(dwarvenHonor));
        oHon.setText(valueOf(orcishHonor));

// if any of the resources dropped to 0 or below, the other side won
        if ((dwarvenResources <= 0) || (orcishResources <= 0)) {

// disable all buttons
            orcish1.setEnabled(false);
            orcish2.setEnabled(false);
            orcish3.setEnabled(false);
            orcish0.setEnabled(false);

            dwarf0.setEnabled(false);
            dwarf1.setEnabled(false);
            dwarf2.setEnabled(false);
            dwarf3.setEnabled(false);

// make the correct view visible
            if (dwarvenHonor > orcishHonor) { // the dwarves won
                TextView rep = (TextView) findViewById(R.id.report);
                rep.setVisibility(GONE);

                TextView dWon = (TextView) findViewById(R.id.dwarvesWon);
                dWon.setVisibility(VISIBLE);
            }

            if (orcishHonor > dwarvenHonor) { // the orcs won
                TextView rep = (TextView) findViewById(R.id.report);
                rep.setVisibility(GONE);

                TextView oWon = (TextView) findViewById(R.id.orcsWon);
                oWon.setVisibility(VISIBLE);
            }

            if (orcishHonor == dwarvenHonor) { // tie
                r = r + "\n" + getString(R.string.nowinner);
            }
        }

// displaying the report text
        TextView rep = (TextView) findViewById(R.id.report);
        rep.setText(r);
    }

    private void switchSides() {

// switch the states of the buttons
        orcish0.setEnabled(!orcish0.isEnabled());
        orcish1.setEnabled(!orcish1.isEnabled());
        orcish2.setEnabled(!orcish2.isEnabled());
        orcish3.setEnabled(!orcish3.isEnabled());

        dwarf0.setEnabled(!dwarf0.isEnabled());
        dwarf1.setEnabled(!dwarf1.isEnabled());
        dwarf2.setEnabled(!dwarf2.isEnabled());
        dwarf3.setEnabled(!dwarf3.isEnabled());

// and switch group (0: dwarves, 1: orcs)
        activeGroup = 1 - activeGroup;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("dwarvenResources", dwarvenResources);
        savedInstanceState.putInt("orcishResources", orcishResources);
        savedInstanceState.putInt("dwarvenHonor", dwarvenHonor);
        savedInstanceState.putInt("orcishHonor", orcishHonor);
        savedInstanceState.putInt("activeGroup", activeGroup);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        dwarvenResources = savedInstanceState.getInt("dwarvenResources");
        orcishResources = savedInstanceState.getInt("orcishResources");
        dwarvenHonor = savedInstanceState.getInt("dwarvenHonor");
        orcishHonor = savedInstanceState.getInt("orcishHonor");
        activeGroup = savedInstanceState.getInt("activeGroup");
/*
        dRes.setText(String.valueOf(dwarvenResources));
        oRes.setText(String.valueOf(orcishResources));
        oHon.setText(String.valueOf(orcishHonor));
        dHon.setText(String.valueOf(dwarvenHonor));
*/
        orcish1.setEnabled(true);
        orcish2.setEnabled(true);
        orcish3.setEnabled(true);
        orcish0.setEnabled(true);

        dwarf0.setEnabled(true);
        dwarf1.setEnabled(true);
        dwarf2.setEnabled(true);
        dwarf3.setEnabled(true);

        if (activeGroup == 0) {
            orcish1.setEnabled(false);
            orcish2.setEnabled(false);
            orcish3.setEnabled(false);
            orcish0.setEnabled(false);
        } else {
            dwarf0.setEnabled(false);
            dwarf1.setEnabled(false);
            dwarf2.setEnabled(false);
            dwarf3.setEnabled(false);
        }

        displayReport(" ");
    }

    public void rules(View view)    {
        Intent intent = new Intent(this, Showrules.class);
        startActivity(intent);
    }
}
