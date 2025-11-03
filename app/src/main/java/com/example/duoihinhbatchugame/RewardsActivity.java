package com.example.duoihinhbatchugame;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class RewardsActivity extends AppCompatActivity {

    private static final String PREF_NAME = "appData";
    private static final String KEY_DAILY_REWARD_DATE = "daily_reward_date";
    private static final String KEY_RANDOM_REWARD_DATE = "random_reward_date";
    private static final String KEY_TIEN = "tien";
    private static final int DAILY_REWARD_AMOUNT = 20;

    private LinearLayout btnReward2Layout;
    private TextView btnReward2Text;
    private ImageView btnReward2CoinIcon;
    
    private LinearLayout btnReward6Layout;
    private TextView btnReward6Text;
    private ImageView btnReward6CoinIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        // Find the reward button elements
        btnReward2Layout = findViewById(R.id.btnReward2Layout);
        if (btnReward2Layout == null) {
            // Try finding by the LinearLayout that contains the button
            View reward2View = findViewById(R.id.reward2);
            if (reward2View instanceof LinearLayout) {
                LinearLayout reward2Layout = (LinearLayout) reward2View;
                // Find the button layout inside (it's the last child)
                int childCount = reward2Layout.getChildCount();
                if (childCount > 0) {
                    View lastChild = reward2Layout.getChildAt(childCount - 1);
                    if (lastChild instanceof LinearLayout) {
                        btnReward2Layout = (LinearLayout) lastChild;
                    }
                }
            }
        }
        btnReward2Text = findViewById(R.id.btnReward2);
        
        // Find the coin icon inside the button layout
        if (btnReward2Layout != null) {
            int childCount = btnReward2Layout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = btnReward2Layout.getChildAt(i);
                if (child instanceof ImageView) {
                    btnReward2CoinIcon = (ImageView) child;
                    break;
                }
            }
        }

        // Find reward 6 button elements (random reward)
        btnReward6Layout = findViewById(R.id.btnReward6Layout);
        if (btnReward6Layout == null) {
            // Try finding by the LinearLayout that contains the button
            View reward6View = findViewById(R.id.reward6);
            if (reward6View instanceof LinearLayout) {
                LinearLayout reward6Layout = (LinearLayout) reward6View;
                // Find the button layout inside (it's the last child)
                int childCount = reward6Layout.getChildCount();
                if (childCount > 0) {
                    View lastChild = reward6Layout.getChildAt(childCount - 1);
                    if (lastChild instanceof LinearLayout) {
                        btnReward6Layout = (LinearLayout) lastChild;
                    }
                }
            }
        }
        btnReward6Text = findViewById(R.id.btnReward6);
        
        // Find the coin icon inside the reward 6 button layout
        if (btnReward6Layout != null) {
            int childCount = btnReward6Layout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = btnReward6Layout.getChildAt(i);
                if (child instanceof ImageView) {
                    btnReward6CoinIcon = (ImageView) child;
                    break;
                }
            }
        }

        // Check and update daily reward button state
        checkDailyRewardStatus();
        
        // Check and update random reward button state
        checkRandomRewardStatus();
    }

    /**
     * Check if daily reward was already claimed today
     */
    private boolean isDailyRewardClaimedToday() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        String lastClaimDate = settings.getString(KEY_DAILY_REWARD_DATE, "");
        String today = getTodayDateString();
        return today.equals(lastClaimDate);
    }

    /**
     * Get today's date as a string (YYYY-MM-DD format)
     */
    private String getTodayDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(new Date());
    }

    /**
     * Check if random reward was already claimed today
     */
    private boolean isRandomRewardClaimedToday() {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        String lastClaimDate = settings.getString(KEY_RANDOM_REWARD_DATE, "");
        String today = getTodayDateString();
        return today.equals(lastClaimDate);
    }

    /**
     * Check daily reward status and update button appearance
     */
    private void checkDailyRewardStatus() {
        boolean claimed = isDailyRewardClaimedToday();
        updateDailyRewardButton(claimed);
    }

    /**
     * Check random reward status and update button appearance
     */
    private void checkRandomRewardStatus() {
        boolean claimed = isRandomRewardClaimedToday();
        updateRandomRewardButton(claimed);
    }

    /**
     * Update the random reward button appearance based on claim status
     */
    private void updateRandomRewardButton(boolean alreadyClaimed) {
        if (btnReward6Layout != null) {
            btnReward6Layout.setEnabled(!alreadyClaimed);
            btnReward6Layout.setClickable(!alreadyClaimed);
            btnReward6Layout.setAlpha(alreadyClaimed ? 0.5f : 1.0f);
        }
        if (btnReward6Text != null) {
            if (alreadyClaimed) {
                btnReward6Text.setText("Đã nhận");
                // Hide coin icon when already claimed
                if (btnReward6CoinIcon != null) {
                    btnReward6CoinIcon.setVisibility(View.GONE);
                }
            } else {
                btnReward6Text.setText("?");
                // Show coin icon when available
                if (btnReward6CoinIcon != null) {
                    btnReward6CoinIcon.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * Update the daily reward button appearance based on claim status
     */
    private void updateDailyRewardButton(boolean alreadyClaimed) {
        if (btnReward2Layout != null) {
            btnReward2Layout.setEnabled(!alreadyClaimed);
            btnReward2Layout.setClickable(!alreadyClaimed);
            btnReward2Layout.setAlpha(alreadyClaimed ? 0.5f : 1.0f);
        }
        if (btnReward2Text != null) {
            if (alreadyClaimed) {
                btnReward2Text.setText("Đã nhận");
                // Hide coin icon when already claimed
                if (btnReward2CoinIcon != null) {
                    btnReward2CoinIcon.setVisibility(View.GONE);
                }
            } else {
                btnReward2Text.setText("20");
                // Show coin icon when available
                if (btnReward2CoinIcon != null) {
                    btnReward2CoinIcon.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    /**
     * Add coins to user's account (same way as PlayActivity)
     */
    private void addCoins(int amount) {
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        int currentTien = settings.getInt(KEY_TIEN, 10);
        int newTien = currentTien + amount;

        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(KEY_TIEN, newTien);
        editor.commit();
    }

    public void closeRewards(View view) {
        VibrationUtils.vibrate(this);
        finish();
    }

    public void claimReward2(View view) {
        VibrationUtils.vibrate(this);

        // Check if already claimed today
        if (isDailyRewardClaimedToday()) {
            Toast.makeText(this, "Bạn đã nhận thưởng hôm nay rồi! Vui lòng quay lại ngày mai.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mark as claimed today
        String today = getTodayDateString();
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_DAILY_REWARD_DATE, today);
        editor.commit();

        // Add coins
        addCoins(DAILY_REWARD_AMOUNT);

        // Update button state
        updateDailyRewardButton(true);

        Toast.makeText(this, "Đã nhận " + DAILY_REWARD_AMOUNT + " xu!", Toast.LENGTH_SHORT).show();
    }

    // COMMENTED OUT: Chia sẻ ứng dụng button
    /*
    public void claimReward3(View view) {
        VibrationUtils.vibrate(this);
        Toast.makeText(this, "Đã nhận 50 xu!", Toast.LENGTH_SHORT).show();
        // TODO: Implement coin reward logic
    }
    */

    /**
     * Generate a random coin amount from 5 to 30 (multiples of 5)
     * with weighted probability - larger amounts have lower probability
     * 
     * Probability distribution:
     * - 5:  30% (highest)
     * - 10: 25%
     * - 15: 20%
     * - 20: 15%
     * - 25: 7%
     * - 30: 3% (lowest)
     */
    private int getRandomCoinAmount() {
        Random random = new Random();
        int roll = random.nextInt(100); // 0-99
        
        // Weighted random selection
        if (roll < 30) {
            return 5;  // 30% chance
        } else if (roll < 55) {
            return 10; // 25% chance (30-54)
        } else if (roll < 75) {
            return 15; // 20% chance (55-74)
        } else if (roll < 90) {
            return 20; // 15% chance (75-89)
        } else if (roll < 97) {
            return 25; // 7% chance (90-96)
        } else {
            return 30; // 3% chance (97-99)
        }
    }

    public void claimReward6(View view) {
        VibrationUtils.vibrate(this);
        
        // Check if already claimed today
        if (isRandomRewardClaimedToday()) {
            Toast.makeText(this, "Bạn đã nhận thưởng hôm nay rồi! Vui lòng quay lại ngày mai.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Generate random coin amount (5-30, multiples of 5)
        int randomAmount = getRandomCoinAmount();
        
        // Mark as claimed today
        String today = getTodayDateString();
        SharedPreferences settings = getSharedPreferences(PREF_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_RANDOM_REWARD_DATE, today);
        editor.commit();
        
        // Add coins to user account
        addCoins(randomAmount);
        
        // Update button state
        updateRandomRewardButton(true);
        
        Toast.makeText(this, "Đã nhận " + randomAmount + " xu!", Toast.LENGTH_SHORT).show();
    }
}

