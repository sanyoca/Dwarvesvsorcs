package com.example.sanya.dwarvesvsorcs;

import android.content.Intent;
import android.graphics.Typeface;
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
    int intDwarvenRes = 10000;
    int intOrcishRes = 10000;
    int intDwarvenHonor = 0;
    int intOrcishHonor = 0;
    int intActiveGroup = 0;

    Button buttonDwarf0;
    Button buttonDwarf1;
    Button buttonDwarf2;
    Button buttonDwarf3;
    Button buttonOrcish0;
    Button buttonOrcish1;
    Button buttonOrcish2;
    Button buttonOrcish3;

    TextView textDwarvenRes;
    TextView textOrcishRes;
    TextView textDwarvenHonor;
    TextView textOrcishHonor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing the global views
        buttonDwarf0 = (Button) findViewById(R.id.dwarf0);
        buttonDwarf1 = (Button) findViewById(R.id.dwarf1);
        buttonDwarf2 = (Button) findViewById(R.id.dwarf2);
        buttonDwarf3 = (Button) findViewById(R.id.dwarf3);
        buttonOrcish0 = (Button) findViewById(R.id.orcish0);
        buttonOrcish1 = (Button) findViewById(R.id.orcish1);
        buttonOrcish2 = (Button) findViewById(R.id.orcish2);
        buttonOrcish3 = (Button) findViewById(R.id.orcish3);
        // Get/initialize the textviews that display the honors and resources
        textDwarvenRes = (TextView) findViewById(R.id.dwarvenResources);
        textOrcishRes = (TextView) findViewById(R.id.orcishResources);
        textDwarvenHonor = (TextView) findViewById(R.id.dwarvenHonor);
        textOrcishHonor = (TextView) findViewById(R.id.orcishHonor);

        // Setting the onclicklisteners
        buttonDwarf0.setOnClickListener(this);
        buttonDwarf1.setOnClickListener(this);
        buttonDwarf2.setOnClickListener(this);
        buttonDwarf3.setOnClickListener(this);
        buttonOrcish0.setOnClickListener(this);
        buttonOrcish1.setOnClickListener(this);
        buttonOrcish2.setOnClickListener(this);
        buttonOrcish3.setOnClickListener(this);
        findViewById(R.id.button_peace).setOnClickListener(this);
        findViewById(R.id.showrules).setOnClickListener(this);

        // Set the custom font for all the views, source: http://www.fontspace.com/pete-klassen/ringbearer
        Typeface fancyFont;
        fancyFont = Typeface.createFromAsset(getAssets(), "fonts/ringbearer.ttf");

        ((TextView) findViewById(R.id.text_res_dwarf)).setTypeface(fancyFont);
        ((TextView) findViewById(R.id.text_res_orc)).setTypeface(fancyFont);
        ((TextView) findViewById(R.id.text_dwarfhonor)).setTypeface(fancyFont);
        ((TextView) findViewById(R.id.text_orchonor)).setTypeface(fancyFont);
        textOrcishHonor.setTypeface(fancyFont);
        textDwarvenHonor.setTypeface(fancyFont);
        textOrcishRes.setTypeface(fancyFont);
        textDwarvenRes.setTypeface(fancyFont);
        ((TextView) findViewById(R.id.report)).setTypeface(fancyFont);
        ((TextView) findViewById(R.id.whoWon)).setTypeface(fancyFont);

        // Set the custom font for the buttons as well
        ((Button) findViewById(R.id.button_peace)).setTypeface(fancyFont);
        buttonDwarf0.setTypeface(fancyFont);
        buttonDwarf1.setTypeface(fancyFont);
        buttonDwarf2.setTypeface(fancyFont);
        buttonDwarf3.setTypeface(fancyFont);
        buttonOrcish0.setTypeface(fancyFont);
        buttonOrcish1.setTypeface(fancyFont);
        buttonOrcish2.setTypeface(fancyFont);
        buttonOrcish3.setTypeface(fancyFont);

        // Which side starts? The other side's buttons will be disabled
        int start = (int) (Math.random() * 100) + 1;
        if (start <= 50) {
            buttonOrcish0.setEnabled(!buttonOrcish0.isEnabled());
            buttonOrcish1.setEnabled(!buttonOrcish1.isEnabled());
            buttonOrcish2.setEnabled(!buttonOrcish2.isEnabled());
            buttonOrcish3.setEnabled(!buttonOrcish3.isEnabled());
            intActiveGroup = 0; // The dwarves begin
        } else {
            buttonDwarf0.setEnabled(!buttonDwarf0.isEnabled());
            buttonDwarf1.setEnabled(!buttonDwarf1.isEnabled());
            buttonDwarf2.setEnabled(!buttonDwarf2.isEnabled());
            buttonDwarf3.setEnabled(!buttonDwarf3.isEnabled());
            intActiveGroup = 1; // The orcs begin
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // They made peace - lol, like that'd ever happen
            case R.id.button_peace:
                peaceTreaty();
                break;
            // The dwarves attack
            case R.id.dwarf1:
            case R.id.dwarf2:
            case R.id.dwarf3:
                dwarvesAttack(Integer.valueOf(v.getTag().toString()));
                break;
            // The orcs attack
            case R.id.orcish1:
            case R.id.orcish2:
            case R.id.orcish3:
                orcsAttack(Integer.valueOf(v.getTag().toString()));
                break;
            // The dwarves collect resources
            case R.id.dwarf0:
                collect(0);
                break;
            // The orcs collect resources
            case R.id.orcish0:
                collect(1);
                break;
            // Display the rules in a new activity
            case R.id.showrules:
                Intent intent = new Intent(this, Showrules.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * This method is called when a dwarven action is executed
     *
     * @param multiply shows the difficulty and therefore the loss/win resource multiplier and the honor points that could be gained
     */
    public void dwarvesAttack(int multiply) {
        // Generating the the attack and defense rolls
        int attackRoll = (int) ((Math.random() * 100) + 1);
        int defenseRoll = (int) ((Math.random() * 100) + 1);
        String report;

        // If the dwarves (the attackers) won
        if (attackRoll >= defenseRoll) {
            report = getString(R.string.dwarfwin) + "\nThey gained " + String.valueOf(multiply) + " honor.";
            intDwarvenHonor = intDwarvenHonor + multiply;
            intDwarvenRes = intDwarvenRes - (defenseRoll * 10 * multiply);
            intOrcishRes = intOrcishRes - ((attackRoll - defenseRoll) * 10 * multiply);
        } else {
            // If they lost
            report = getString(R.string.dwarflost);
            intDwarvenRes = intDwarvenRes - (attackRoll * 10 * multiply);
            intOrcishRes = intOrcishRes - ((defenseRoll - attackRoll) * 10 * multiply);
        }
        // Disable the dwarves button - it's the orcs' turn, so activate their buttons
        switchSides();
        // Displaying the report
        displayReport(report);
    }

    /**
     * This method is called when an orcish action is executed
     *
     * @param multiply shows the difficulty and therefore the loss/win resource multiplier and the honor points that could be gained
     */
    public void orcsAttack(int multiply) {
        // Generating the the attack and defense rolls
        int attackRoll = (int) ((Math.random() * 100) + 1);
        int defenseRoll = (int) ((Math.random() * 100) + 1);
        String report;

        // If the orcs (the attackers) won
        if (attackRoll >= defenseRoll) {
            report = getString(R.string.orcwin) + "\nThey gained " + String.valueOf(multiply) + " honor.";
            intOrcishHonor = intOrcishHonor + multiply;
            intOrcishRes = intOrcishRes - (defenseRoll * 10 * multiply);
            intDwarvenRes = intDwarvenRes - ((attackRoll - defenseRoll) * 10 * multiply);
        } else {
            // If they lost
            report = getString(R.string.orclost);
            intOrcishRes = intOrcishRes - (attackRoll * 10 * multiply);
            intDwarvenRes = intDwarvenRes - ((defenseRoll - attackRoll) * 10 * multiply);
        }
        // Disable the orcs button - it's the dwarves' turn, so activate their buttons
        switchSides();
        // Displaying report
        displayReport(report);
    }

    /**
     * Method for resource collection
     *
     * @param whichSide shows which side (0:dwarves, 1:orcs) collected resources
     */
    public void collect(int whichSide) {
        String report;
        // Generating a random number of resources (500-1000)
        int collectedResources = 500 + (int) (Math.random() * 500) + 1;
        // Adding it to the current resource value
        if (whichSide == 0) {
            intDwarvenRes = intDwarvenRes + collectedResources;
            report = getString(R.string.dwarfharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        } else {
            intOrcishRes = intOrcishRes + collectedResources;
            report = getString(R.string.orcharvest) + " " + valueOf(collectedResources) + " " + getString(R.string.harvestres) + ".";
        }
        displayReport(report);
        switchSides();
    }

    /**
     * This method is for resetting purposes, stating that the orcs and dwarves came to a peace agreement (and again: lol)
     */
    public void peaceTreaty() {
        // Setting the variables to their start state
        intDwarvenRes = 10000;
        intOrcishRes = 10000;
        intDwarvenHonor = 0;
        intOrcishHonor = 0;

        // Getting the resource TextViews and set their text to the variable value
        textDwarvenRes.setText(String.valueOf(intDwarvenRes));
        textOrcishRes.setText(String.valueOf(intOrcishRes));

        // Doing the same for the honor points
        textOrcishHonor.setText(String.valueOf(intOrcishHonor));
        textDwarvenHonor.setText(String.valueOf(intDwarvenHonor));
        displayReport(getString(R.string.atpeace));

        // Enable all buttons
        buttonOrcish1.setEnabled(true);
        buttonOrcish2.setEnabled(true);
        buttonOrcish3.setEnabled(true);
        buttonOrcish0.setEnabled(true);
        buttonDwarf0.setEnabled(true);
        buttonDwarf1.setEnabled(true);
        buttonDwarf2.setEnabled(true);
        buttonDwarf3.setEnabled(true);

        // The winning report view have to go
        TextView whoWon = (TextView) findViewById(R.id.whoWon);
        whoWon.setVisibility(GONE);
        // The normal report view (now with the peace treaty message) has to appear
        TextView rep = (TextView) findViewById(R.id.report);
        rep.setVisibility(VISIBLE);

        // Which side starts next time? The other side's buttons will be disabled
        int start = (int) (Math.random() * 100) + 1;
        if (start <= 50) {
            buttonOrcish0.setEnabled(!buttonOrcish0.isEnabled());
            buttonOrcish1.setEnabled(!buttonOrcish1.isEnabled());
            buttonOrcish2.setEnabled(!buttonOrcish2.isEnabled());
            buttonOrcish3.setEnabled(!buttonOrcish3.isEnabled());
            intActiveGroup = 0; // The dwarves begin
        } else {
            buttonDwarf0.setEnabled(!buttonDwarf0.isEnabled());
            buttonDwarf1.setEnabled(!buttonDwarf1.isEnabled());
            buttonDwarf2.setEnabled(!buttonDwarf2.isEnabled());
            buttonDwarf3.setEnabled(!buttonDwarf3.isEnabled());
            intActiveGroup = 1; // The orcs begin
        }
    }

    /**
     * This method displays reports.
     *
     * @param r the report to be displayed
     */
    private void displayReport(String r) {
        // Displaying the resource changes on both sides
        textDwarvenRes.setText(String.valueOf(intDwarvenRes));
        textOrcishRes.setText(String.valueOf(intOrcishRes));
        // Displaying the honor points change on both sides
        textDwarvenHonor.setText(String.valueOf(intDwarvenHonor));
        textOrcishHonor.setText(String.valueOf(intOrcishHonor));

        // If any of the resources dropped to 0 or below, check the honor points to determine the winner
        if ((intDwarvenRes <= 0) || (intOrcishRes <= 0)) {
            // Disable all buttons
            buttonOrcish1.setEnabled(false);
            buttonOrcish2.setEnabled(false);
            buttonOrcish3.setEnabled(false);
            buttonOrcish0.setEnabled(false);
            buttonDwarf0.setEnabled(false);
            buttonDwarf1.setEnabled(false);
            buttonDwarf2.setEnabled(false);
            buttonDwarf3.setEnabled(false);
            TextView whoWon = (TextView) findViewById(R.id.whoWon);

            // The report view has to go
            TextView rep = (TextView) findViewById(R.id.report);
            rep.setVisibility(GONE);

            // Make the correct winner view visible
            if (intDwarvenHonor > intOrcishHonor) { // The dwarves won
                whoWon.setText(R.string.dwarveswon);
                whoWon.setVisibility(VISIBLE);
            }

            if (intOrcishHonor > intDwarvenHonor) { // The orcs won
                whoWon.setText(R.string.orcswon);
                whoWon.setVisibility(VISIBLE);
            }

            if (intOrcishHonor == intDwarvenHonor) { // tie
                r = r + "\n" + getString(R.string.nowinner);
            }
        }

        // Displaying the report text
        TextView rep = (TextView) findViewById(R.id.report);
        rep.setText(r);
    }

    /**
     * This method switches between the dwarf/orc side in turns
     */
    private void switchSides() {
        // Switch the states of the buttons
        buttonOrcish0.setEnabled(!buttonOrcish0.isEnabled());
        buttonOrcish1.setEnabled(!buttonOrcish1.isEnabled());
        buttonOrcish2.setEnabled(!buttonOrcish2.isEnabled());
        buttonOrcish3.setEnabled(!buttonOrcish3.isEnabled());

        buttonDwarf0.setEnabled(!buttonDwarf0.isEnabled());
        buttonDwarf1.setEnabled(!buttonDwarf1.isEnabled());
        buttonDwarf2.setEnabled(!buttonDwarf2.isEnabled());
        buttonDwarf3.setEnabled(!buttonDwarf3.isEnabled());
        // And switch group (0: dwarves, 1: orcs)
        intActiveGroup = 1 - intActiveGroup;
    }

    /**
     * On screen rotation - or anything that pauses the run of the app - save the current status of the app
     *
     * @param savedInstanceState a bundle
     */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putInt("intDwarvenRes", intDwarvenRes);
        savedInstanceState.putInt("intOrcishRes", intOrcishRes);
        savedInstanceState.putInt("intDwarvenHonor", intDwarvenHonor);
        savedInstanceState.putInt("intOrcishHonor", intOrcishHonor);
        savedInstanceState.putInt("intActiveGroup", intActiveGroup);
        savedInstanceState.putString("stringReport", ((TextView) findViewById(R.id.report)).getText().toString());
    }

    /**
     * When the app restarts, restore the values
     *
     * @param savedInstanceState a bundle
     */
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        intDwarvenRes = savedInstanceState.getInt("intDwarvenRes");
        intOrcishRes = savedInstanceState.getInt("intOrcishRes");
        intDwarvenHonor = savedInstanceState.getInt("intDwarvenHonor");
        intOrcishHonor = savedInstanceState.getInt("intOrcishHonor");
        intActiveGroup = savedInstanceState.getInt("intActiveGroup");

        buttonOrcish1.setEnabled(true);
        buttonOrcish2.setEnabled(true);
        buttonOrcish3.setEnabled(true);
        buttonOrcish0.setEnabled(true);
        buttonDwarf0.setEnabled(true);
        buttonDwarf1.setEnabled(true);
        buttonDwarf2.setEnabled(true);
        buttonDwarf3.setEnabled(true);

        if (intActiveGroup == 0) {
            buttonOrcish1.setEnabled(false);
            buttonOrcish2.setEnabled(false);
            buttonOrcish3.setEnabled(false);
            buttonOrcish0.setEnabled(false);
        } else {
            buttonDwarf0.setEnabled(false);
            buttonDwarf1.setEnabled(false);
            buttonDwarf2.setEnabled(false);
            buttonDwarf3.setEnabled(false);
        }
        displayReport(savedInstanceState.getString("stringReport"));
    }
}
