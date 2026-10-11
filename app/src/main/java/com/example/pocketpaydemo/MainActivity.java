package com.example.pocketpaydemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    private final int ink = Color.rgb(31, 39, 56);
    private final int muted = Color.rgb(105, 115, 133);
    private final int blue = Color.rgb(26, 115, 232);
    private final int pale = Color.rgb(239, 245, 255);
    private LinearLayout history;
    private TextView balance;
    private int demoBalance = 2450;
    private int transactionCount = 0;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setPadding(dp(20), dp(18), dp(20), dp(28));
        page.setBackgroundColor(Color.rgb(250, 251, 253));
        scroll.addView(page);
        setContentView(scroll);

        LinearLayout top = new LinearLayout(this);
        top.setGravity(Gravity.CENTER_VERTICAL);
        TextView brand = text("PocketPay", 25, ink, true);
        top.addView(brand, new LinearLayout.LayoutParams(0, -2, 1));
        TextView badge = text("DEMO", 12, blue, true);
        badge.setPadding(dp(12), dp(7), dp(12), dp(7));
        badge.setBackground(round(pale, 30));
        top.addView(badge);
        page.addView(top);

        TextView greeting = text("Hello there 👋", 16, muted, false);
        LinearLayout.LayoutParams gp = params(-1, -2);
        gp.topMargin = dp(20);
        page.addView(greeting, gp);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(dp(20), dp(20), dp(20), dp(20));
        card.setBackground(round(blue, 22));
        LinearLayout.LayoutParams cp = params(-1, -2);
        cp.topMargin = dp(12);
        page.addView(card, cp);
        card.addView(text("DEMO WALLET BALANCE", 12, Color.rgb(222, 235, 255), true));
        balance = text("₹2,450.00", 32, Color.WHITE, true);
        LinearLayout.LayoutParams bp = params(-1, -2);
        bp.topMargin = dp(8);
        card.addView(balance, bp);
        TextView disclaimer = text("This is sample money, not a bank balance.", 12, Color.WHITE, false);
        LinearLayout.LayoutParams dp = params(-1, -2);
        dp.topMargin = dp(7);
        card.addView(disclaimer, dp);

        TextView actionsTitle = text("Quick actions", 19, ink, true);
        LinearLayout.LayoutParams atp = params(-1, -2);
        atp.topMargin = dp(24);
        page.addView(actionsTitle, atp);

        LinearLayout actions = new LinearLayout(this);
        actions.setGravity(Gravity.CENTER);
        String[] names = {"Send", "Request", "Scan & Pay", "History"};
        String[] symbols = {"↗", "↓", "▦", "↻"};
        for (int i = 0; i < names.length; i++) {
            final int action = i;
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            TextView icon = text(symbols[i], 24, blue, true);
            icon.setGravity(Gravity.CENTER);
            icon.setBackground(round(pale, 50));
            item.addView(icon, new LinearLayout.LayoutParams(dp(54), dp(54)));
            TextView label = text(names[i], 11, ink, true);
            label.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lp = params(-1, -2);
            lp.topMargin = dp(8);
            item.addView(label, lp);
            item.setOnClickListener(v -> handleAction(action));
            actions.addView(item, new LinearLayout.LayoutParams(0, -2, 1));
        }
        LinearLayout.LayoutParams ap = params(-1, -2);
        ap.topMargin = dp(16);
        page.addView(actions, ap);

        TextView safety = text("Practice mode • No real payments • No bank connection", 12, muted, false);
        safety.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams sp = params(-1, -2);
        sp.topMargin = dp(20);
        page.addView(safety, sp);

        TextView recent = text("Recent activity", 19, ink, true);
        LinearLayout.LayoutParams rp = params(-1, -2);
        rp.topMargin = dp(26);
        page.addView(recent, rp);

        history = new LinearLayout(this);
        history.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams hp = params(-1, -2);
        hp.topMargin = dp(8);
        page.addView(history, hp);
        addHistory("Coffee shop", "Today • Demo transaction", "− ₹120.00", false);
        addHistory("Sample refund", "Yesterday • Demo transaction", "+ ₹250.00", true);
    }

    private void handleAction(int action) {
        if (action == 3) {
            Toast.makeText(this, "Your demo activity is shown below.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (action == 2) {
            new AlertDialog.Builder(this)
                .setTitle("Scan & Pay demo")
                .setMessage("Camera and QR scanning are not enabled in this demo. No payment will be made.")
                .setPositiveButton("OK", null).show();
            return;
        }
        EditText person = new EditText(this);
        person.setHint(action == 0 ? "Recipient name (e.g. Alex)" : "Name to request from");
        EditText amount = new EditText(this);
        amount.setHint("Amount in ₹ (e.g. 50)");
        amount.setInputType(2);
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(dp(22), dp(4), dp(22), dp(4));
        form.addView(person);
        form.addView(amount);
        new AlertDialog.Builder(this)
            .setTitle(action == 0 ? "Send demo money" : "Request demo money")
            .setView(form)
            .setNegativeButton("Cancel", null)
            .setPositiveButton(action == 0 ? "Simulate payment" : "Create request", (dialog, which) -> {
                String who = person.getText().toString().trim();
                String value = amount.getText().toString().trim();
                if (who.isEmpty() || value.isEmpty()) {
                    Toast.makeText(this, "Enter a name and amount.", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    int amountValue = Integer.parseInt(value);
                    if (amountValue <= 0 || amountValue > 100000) throw new NumberFormatException();
                    if (action == 0) {
                        if (amountValue > demoBalance) {
                            Toast.makeText(this, "Not enough demo balance.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        demoBalance -= amountValue;
                        balance.setText(String.format(Locale.getDefault(), "₹%,d.00", demoBalance));
                        addHistory(who, "Just now • Simulated only", "− ₹" + amountValue + ".00", false);
                        Toast.makeText(this, "Demo only: no money was sent.", Toast.LENGTH_LONG).show();
                    } else {
                        addHistory("Request: " + who, "Just now • Not sent to anyone", "₹" + amountValue + ".00", true);
                        Toast.makeText(this, "Demo request created locally only.", Toast.LENGTH_LONG).show();
                    }
                } catch (NumberFormatException ex) {
                    Toast.makeText(this, "Enter a whole amount from ₹1 to ₹100,000.", Toast.LENGTH_SHORT).show();
                }
            }).show();
    }

    private void addHistory(String name, String subtitle, String amount, boolean incoming) {
        LinearLayout row = new LinearLayout(this);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(12), dp(13), dp(12), dp(13));
        row.setBackground(round(Color.WHITE, 16));
        LinearLayout.LayoutParams rowParams = params(-1, -2);
        rowParams.topMargin = dp(7);
        history.addView(row, 0, rowParams);
        TextView circle = text(incoming ? "+" : "↗", 19, incoming ? Color.rgb(20, 145, 90) : blue, true);
        circle.setGravity(Gravity.CENTER);
        circle.setBackground(round(incoming ? Color.rgb(230, 248, 239) : pale, 50));
        row.addView(circle, new LinearLayout.LayoutParams(dp(42), dp(42)));
        LinearLayout labels = new LinearLayout(this);
        labels.setOrientation(LinearLayout.VERTICAL);
        labels.setPadding(dp(11), 0, dp(6), 0);
        labels.addView(text(name, 14, ink, true));
        TextView sub = text(subtitle, 11, muted, false);
        LinearLayout.LayoutParams subp = params(-1, -2);
        subp.topMargin = dp(3);
        labels.addView(sub, subp);
        row.addView(labels, new LinearLayout.LayoutParams(0, -2, 1));
        TextView amt = text(amount, 13, incoming ? Color.rgb(20, 145, 90) : ink, true);
        row.addView(amt);
        transactionCount++;
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value);
        t.setTextSize(size);
        t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }
    private GradientDrawable round(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color);
        d.setCornerRadius(dp(radius));
        return d;
    }
    private LinearLayout.LayoutParams params(int w, int h) {
        return new LinearLayout.LayoutParams(w < 0 ? w : dp(w), h < 0 ? h : dp(h));
    }
    private int dp(int v) {
        return (int)(v * getResources().getDisplayMetrics().density + 0.5f);
    }
}
