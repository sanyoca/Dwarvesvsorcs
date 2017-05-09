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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Define global variables for storing the resources and the honor points, Buttons and other views
     */
    int dwarvenResources = 10000;
    int orcishResources = 10000;
    int dwarvenHonor = 0;
    int orcishHonor = 0;
    int activeGroup = 0;

    Button dwarf0;
    Button dwarf1;
    Button dwarf2;
    Button dwarf3;
    Button orcish0;
    Button orcish1;
    Button orcish2;
    Button orcish3;

    TextView dRes;
    TextView oRes;
    TextView dHon;
    TextView oHon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the global views
        dwarf0 = (Button) findViewById(R.id.dwarf0);
        dwarf1 = (Button) findViewById(R.id.dwarf1);
        dwarf2 = (Button) findViewById(R.id.dwarf2);
        dwarf3 = (Button) findViewById(R.id.dwarf3);
        orcish0 = (Button) findViewById(R.id.orcish0);
        orcish1 = (Button) findViewById(R.id.orcish1);
        orcish2 = (Button) findViewById(R.id.orcish2);
        orcish3 = (Button) findViewById(R.id.orcish3);
        // get the textviews that display the honors and resources
        dRes = (TextView) findViewById(R.id.dwarvenResources);
        oRes = (TextView) findViewById(R.id.orcishResources);
        dHon = (TextView) findViewById(R.id.dwarvenHonor);
        oHon = (TextView) findViewById(R.id.orcishHonor);

        // setting the onclicklisteners
        dwarf0.setOnClickListener(this);
        dwarf1.setOnClickListener(this);
        dwarf2.setOnClickListener(this);
        dwarf3.setOnClickListener(this);
        orcish0.setOnClickListener(this);
        orcish1.setOnClickListener(this);
        orcish2.setOnClickListener(this);
        orcish3.setOnClickListener(this);
        findViewById(R.id.button_peace).setOnClickListener(this);
        findViewById(R.id.showrules).setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_peace:
                peaceTreaty();
                break;
            case R.id.dwarf1:
            case R.id.dwarf2:
            case R.id.dwarf3:
                dwarvesAttack(Integer.valueOf(v.getTag().toString()));
                break;
            case R.id.orcish1:
            case R.id.orcish2:
            case R.id.orcish3:
                orcsAttack(Integer.valueOf(v.getTag().toString()));
                break;
            case R.id.dwarf0:
                collect(0);
                break;
            case R.id.orcish0:
                collect(1);
                break;
            case R.id.showrules:
                Intent intent = new Intent(this, Showrules.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * This method is called when a dwarven button is clicked
     */
    public void dwarvesAttack(int multiply) {
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
        // disable the dwarves button - it's the orcs' turn, so activate their buttons
        switchSides();
        // displaying the report
        displayReport(report);
    }

    /**
     * This method is called when an orcish button is clicked
     */
    public void orcsAttack(int multiply) {
        int attackRoll = (int) ((Math.random() * 100) + 1);
        int defenseRoll = (int) ((Math.random() * 100) + 1);
        String report = "";

        // if the orcs (the attackers) won
        if (attackRoll >= defenseRoll) {
            report = getString(R.string.orcwin);
            orcishHonor = orcishHonor + multiply;
            orcishResources = orcishResources - (defenseRoll * 10 * multiply);
            dwarvenResources = dwarvenResources - ((attackRoll - defenseRoll) * 10 * multiply);
        } else {
            // if they lost
            report = getString(R.string.orclost);
            orcishResources = orcishResources - (attackRoll * 10 * multiply);
            dwarvenResources = dwarvenResources - ((defenseRoll - attackRoll) * 10 * multiply);
        }
        // disable the orcs button - it's the dwarves' turn, so activate their buttons
        switchSides();
        // displaying report
        displayReport(report);
    }

    /**
     * Method for resource collection
     */
    public void collect(int whichSide) {
        String report;
        // generating a random number of resources (500-1000)
        int collectedResources = 500 + (int) (Math.random() * 500) + 1;
        // adding it to the current resource value
        if (whichSide == 0) {
            dwarvenResources = dwarvenResources + collectedResources;
            report = getString(R.string.dwarfharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        } else {
            orcishResources = orcishResources + collectedResources;
            report = getString(R.string.orcharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        }
        displayReport(report);
        switchSides();
    }

    /**
     * This method is for resetting purposes, stating that the orcs and dwarves came to a peace agreement (lol)
     */
    public void peaceTreaty() {
        // setting the variables to their start state
        dwarvenResources = 10000;
        orcishResources = 10000;
        dwarvenHonor = 0;
        orcishHonor = 0;

        // getting the resource TextViews and set their text to the variable value
        dRes.setText(String.valueOf(dwarvenResources));
        oRes.setText(String.valueOf(orcishResources));

        // doing the same for the honor points
        oHon.setText(String.valueOf(orcishHonor));
        dHon.setText(String.valueOf(dwarvenHonor));
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

        // the winning report views have to go
        TextView dWon = (TextView) findViewById(R.id.dwarvesWon);
        dWon.setVisibility(GONE);
        TextView oWon = (TextView) findViewById(R.id.orcsWon);
        oWon.setVisibility(GONE);
        // the normal report view (now with the peace treaty message) has to appear
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

    /**
     * This method displays reports.
     *
     * @param r the report to be displayed
     */
    private void displayReport(String r) {
        // displaying the resource changes on both sides
        dRes.setText(String.valueOf(dwarvenResources));
        oRes.setText(String.valueOf(orcishResources));
        // displaying the honor points change on both sides
        dHon.setText(String.valueOf(dwarvenHonor));
        oHon.setText(String.valueOf(orcishHonor));

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

            // make the correct winner view visible
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

    /**
     * This method switches between the dwarf/orc side in turns
     */
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

    /**
     * On screen rotation - or anything that stops the run of the app - save the current status of the app
     *
     * @param savedInstanceState
     */
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

    /**
     * When the app restarts, restore the values
     *
     * @param savedInstanceState
     */
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
}
