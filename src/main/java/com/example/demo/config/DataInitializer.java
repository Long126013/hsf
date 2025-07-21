package com.example.demo.config;

import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.service.ProductService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@AllArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private UserService userService;
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        // <--- IMPORTANT CHANGE: Use actual email format for usernames and plain-text passwords

        userInit();
        productInit();
    }

    public void userInit(){
        try{
            userService.createUser(new User("admin@example.com", "admin", 0));
            userService.createUser(new User("test@example.com", "test", 0));
            userService.createUser(new User("user@example.com", "user", 0));
            userService.createUser(new User("ngoctrinh@example.com", "null", 0));
            userService.createUser(new User("t","t", 0));

            log.info("User initialization complete");
        } catch(Exception ex){
            ex.printStackTrace();
            log.info("User initialization failed", ex.getMessage());
        }
    }

    public void productInit(){
        try{
            productService.createProduct(new Product("Product with Zero Quantity", 0, 100)); // Zero quantity
            productService.createProduct(new Product("Product with Max Quantity", 999999, 1)); // Very high quantity
            productService.createProduct(new Product("Free Sample", 10000, 0)); // Zero price
            productService.createProduct(new Product("Luxury Yacht (Pre-owned)", 1, 500_000_000)); // Very high price

            productService.createProduct(new Product("Cardboard Box", 100, 20));
            productService.createProduct(new Product("Grenade (fake)", 50, 200));
            productService.createProduct(new Product("Samsung Galaxy", 1, 10_000));
            productService.createProduct(new Product("Pirated Minecraft", 4, 10));

            productService.createProduct(new Product("Wireless Mouse", 100, 299)); // Ergonomic design, USB receiver included
            productService.createProduct(new Product("USB-C Charging Cable", 200, 99)); // Fast charging compatible
            productService.createProduct(new Product("Mechanical Keyboard (RGB)", 50, 1999)); // For typing and looking cool while doing it
            productService.createProduct(new Product("External SSD 1TB", 30, 2499)); // Portable, fast, durable
            productService.createProduct(new Product("Laptop Stand", 80, 349)); // Adjustable height for better posture
            productService.createProduct(new Product("Noise-Cancelling Headphones", 40, 2999)); // Block out your coworkers — and your thoughts
            productService.createProduct(new Product("1080p Webcam", 60, 699)); // Clear video for meetings you wish you could skip
            productService.createProduct(new Product("Smart LED Desk Lamp", 25, 499)); // Adjustable brightness and color temperature
            productService.createProduct(new Product("Blue Light Blocking Glasses", 120, 159)); // Protects your eyes from all-night debugging sessions
            productService.createProduct(new Product("Portable Power Bank 10,000mAh", 75, 399)); // Keep devices alive — unlike your group project
            productService.createProduct(new Product("Monitor Cleaning Kit", 100, 89)); // For the few who clean their screens before they die of shame
            productService.createProduct(new Product("Laptop Sleeve 15-inch", 90, 259)); // Shockproof and water-resistant
            productService.createProduct(new Product("Bluetooth Speaker", 60, 799)); // For blasting Lo-fi while pretending to work
            productService.createProduct(new Product("Ergonomic Office Chair", 15, 4499)); // Adjustable lumbar support, mesh back
            productService.createProduct(new Product("Standing Desk (Electric)", 10, 7999)); // Dual-motor lift, memory presets
            productService.createProduct(new Product("Bookshelf - 5 Tier", 25, 1299)); // Sturdy enough to hold unread programming books
            productService.createProduct(new Product("Compact Folding Table", 30, 899)); // Space-saving and apartment-approved
            productService.createProduct(new Product("Adjustable Desk Lamp", 50, 349)); // Warm/cool lighting modes
            productService.createProduct(new Product("Bean Bag Chair", 20, 599)); // For unproductive brainstorming sessions
            productService.createProduct(new Product("Minimalist Coffee Table", 18, 999)); // Glass top with metal frame
            productService.createProduct(new Product("Laptop Bed Tray", 40, 499)); // For peak remote work laziness
            productService.createProduct(new Product("Wooden Nightstand", 22, 799)); // Drawer glides smoother than your last relationship
            productService.createProduct(new Product("Corner Workstation Desk", 12, 2999)); // Designed for code and crumbs
            productService.createProduct(new Product("Bar Stool (Set of 2)", 14, 1099)); // Surprisingly uncomfortable, still stylish
            productService.createProduct(new Product("Recliner Chair", 8, 5599)); // For those long post-deploy naps
            productService.createProduct(new Product("Two-Door Wardrobe", 10, 2599)); // Hide your tech shirts and emotional baggage
            productService.createProduct(new Product("Shoe Rack Bench", 35, 399)); // Sit while you judge your life decisions


            productService.createProduct(new Product("Existential Crisis Starter Pack", 1, 0)); // Includes mirror, journal, and rainy playlist
            productService.createProduct(new Product("Thoughts and Prayers", 999, 0)); // Guaranteed to change absolutely nothing
            productService.createProduct(new Product("Hope (Out of Stock)", 0, 9999));
            productService.createProduct(new Product("Life Manual (Blank)", 42, 42)); // All answers lead to confusion
            productService.createProduct(new Product("Bag of Red Flags", 200, 10)); // Popular in dating section
            productService.createProduct(new Product("Will to Live (Leased)", 1, 404)); // Not found
            productService.createProduct(new Product("Empty Promise", 9999, 0)); // Best seller during election season
            productService.createProduct(new Product("Intern’s Soul", 1, 1));

            productService.createProduct(new Product("Junior Dev Confidence Pack", 50, 10)); // Contains 0 actual experience, but 100 StackOverflow tabs
            productService.createProduct(new Product("Senior Developer Burnout Kit", 1, 0)); // Includes 10 years of broken dreams and one unread therapy email
            productService.createProduct(new Product("Legacy Code DNA Sample", 3, 999)); // Origin unknown. May contain sentient bugs
            productService.createProduct(new Product("Production Access (Unsupervised)", 1, 404)); // Grant it if you hate your sleep schedule
            productService.createProduct(new Product("Scrum Meeting Repellent", 99, 50)); // Apply daily, still won’t work
            productService.createProduct(new Product("DevOps Voodoo Doll", 5, 13)); // Poke to trigger random Jenkins failures
            productService.createProduct(new Product("Database Migration Roulette", 7, 77)); // Every click is a new outage
            productService.createProduct(new Product("Bug That Only Appears in Demos", 1000, 1)); // Powered by live pressure and public shame
            productService.createProduct(new Product("404 Page Generator", 404, 404)); // Now with existential quotes
            productService.createProduct(new Product("Hotfix That Broke Everything", 9999, 0)); // Still marked as “critical”
            productService.createProduct(new Product("AI Assistant That Gaslights You", 100, 100)); // “You wrote this code, remember?”
            productService.createProduct(new Product("VPN That Never Connects", 99, 19)); // Designed by the HR compliance team
            productService.createProduct(new Product("‘It Works On My Machine’ Sticker", 3000, 1)); // Comes with sarcasm pre-installed
            productService.createProduct(new Product("Zero-Day Surprise Box", 1, 0)); // You didn’t know you ordered it, but it’s here
            productService.createProduct(new Product("Documentation (TODO)", 500, 10)); // Incomplete by design

            productService.createProduct(new Product("Team Member Who Ghosts", 1, 0)); // Assigned to ‘Research,’ last seen online 4 weeks ago
            productService.createProduct(new Product("Deadline Extension Prayer Candle", 50, 10)); // Burn one, sacrifice sleep
            productService.createProduct(new Product("Project Plan (Fantasy Edition)", 5, 999)); // Tasks: ✅✅✅ — Reality: ❌❌❌
            productService.createProduct(new Product("‘Let’s Circle Back’ Button", 100, 5)); // Translates to “we have no idea”
            productService.createProduct(new Product("Passive-Aggressive Merge Request", 12, 0)); // Includes 42 comments and no actual fixes
            productService.createProduct(new Product("Last-Minute Savior", 1, 10000)); // Arrives 5 minutes before demo, saves everyone, still underpaid
            productService.createProduct(new Product("Deadline Reminder That Induces Panic", 500, 15)); // Sends email, Slack ping, and existential crisis
            productService.createProduct(new Product("Team Leader’s Breakdown Kit", 3, 7)); // Includes duct tape, tissues, and a fake smile
            productService.createProduct(new Product("One Guy Doing All The Work", 1, 1)); // You know who you are. We salute your suffering
            productService.createProduct(new Product("Meeting That Could’ve Been An Email", 1000, 0)); // Still mandatory
            productService.createProduct(new Product("PowerPoint With No Content", 200, 10)); // 50 slides. 0 ideas
            productService.createProduct(new Product("Excuse Generator", 10, 3)); // “My laptop exploded” is now option #2
            productService.createProduct(new Product("Collaboration Platform That Divides", 1, 100)); // GitHub? More like BlameHub
            productService.createProduct(new Product("Deadline (Yesterday)", 999, 0)); // Already missed. Already judged
            productService.createProduct(new Product("Feedback Loop of Doom", 666, 6)); // “Great work! But change everything.”
            productService.createProduct(new Product("Team Member Who Only Shows Up on Demo Day", 1, 2)); // “I helped test it... emotionally.”
            productService.createProduct(new Product("Scope Creep Expansion Pack", 100, 200)); // Project grows. Morale shrinks.
            productService.createProduct(new Product("Shared Responsibility (Undefined)", 1000, 1)); // When everyone owns it, no one does
            productService.createProduct(new Product("Group Chat Full of Silence", 999, 0)); // Seen by everyone. Replied by none.
            productService.createProduct(new Product("Sprint That Ends in Chaos", 20, 13)); // Velocity: 0. Damage: High

            productService.createProduct(new Product("Pirated Adobe Suite (Lifetime)", 5, 50)); // Activate via instructions.txt (and guilt)
            productService.createProduct(new Product("USB Drive Full of Malware Samples", 3, 25)); // For *educational* purposes, of course
            productService.createProduct(new Product("Weed Gummy (Unlabeled)", 15, 199)); // Found in the HR break room
            productService.createProduct(new Product("Leaked Exam Answers PDF", 7, 10)); // Midterms not included
            productService.createProduct(new Product("Fake University Degree Generator", 1, 499)); // Comes with 10+ years of impostor syndrome
            productService.createProduct(new Product("Nude.js (Open Source, NSFW)", 69, 0)); // Front-end exposed. Literally.
            productService.createProduct(new Product("Crypto Miner Hidden in Screensaver", 12, 0)); // Why is your GPU screaming?
            productService.createProduct(new Product("Unlicensed Antivirus", 30, 99)); // Looks legit. Isn’t.
            productService.createProduct(new Product("Weird AI Voice Clone Tool", 8, 999)); // Clone your boss. Confuse HR.
            productService.createProduct(new Product("Netflix Shared Account (Stolen)", 4, 5)); // You *might* get logged out at 3am
            productService.createProduct(new Product("VPN with Questionable Origins", 20, 39)); // Routes through someone’s microwave in Russia
            productService.createProduct(new Product("Phone Unlock Tool (Totally Not for Stolen Phones)", 1, 250)); // Use responsibly. Or not.
            productService.createProduct(new Product("Keylogger Kit (DIY)", 6, 120)); // For research. Allegedly.
            productService.createProduct(new Product("Anonymous Mail Bomb (Glitter)", 13, 420)); // Harmless. Mostly.
            productService.createProduct(new Product("Used Office Chair from Blacksite", 2, 69)); // Stains may or may not be classified
            productService.createProduct(new Product("Illegal Fireworks Pack (Imported)", 10, 999)); // Celebrate... quietly... somewhere else

            productService.createProduct(new Product("Beginner's Guide to Hotwiring Scooters", 7, 420)); // For educational purposes only, officer
            productService.createProduct(new Product("Social Engineering 101: Getting Passwords With Charm", 5, 999)); // Step 1: Pretend you're IT
            productService.createProduct(new Product("How to Fake Credentials Like a Pro", 3, 850)); // Now includes LinkedIn profile templates
            productService.createProduct(new Product("Dumpster Diving for Data", 4, 199)); // Smells like breach potential
            productService.createProduct(new Product("Cracking Wi-Fi with Grandma's Laptop", 6, 666)); // Because public networks are for cowards
            productService.createProduct(new Product("Voice Cloning for Vishing Attacks", 2, 1337)); // Fool HR, call in your own sick days
            productService.createProduct(new Product("Bypass 2FA Using Tears and Guilt", 1, 300)); // Emotional exploits included
            productService.createProduct(new Product("Build Your Own Skimmer Kit", 3, 750)); // Includes duct tape and moral ambiguity
            productService.createProduct(new Product("Disguise Generator for Physical Pen Tests", 8, 599)); // Includes 1 janitor vest and fake badge
            productService.createProduct(new Product("Fake Website Design for Phishing", 5, 999)); // Pixel-perfect. Legally gray.
            productService.createProduct(new Product("Illegal Surveillance for Dummies", 1, 500)); // Warning: You are now on *your* own watchlist
            productService.createProduct(new Product("Evil USB Tricks: Plug It and Regret Nothing", 4, 420)); // Reverse charging? More like reverse ownership
            productService.createProduct(new Product("Hack the Planet: 1990s Techniques That Still Work", 10, 199)); // Nostalgic. Illegal.
            productService.createProduct(new Product("How to Vanish Digitally (and Physically)", 2, 3999)); // Final chapter redacted
            productService.createProduct(new Product("Git Rebase Explained as a Crime", 100, 0)); // Technically not illegal, but morally yes

            productService.createProduct(new Product("E.G.O Suitcase", 4, 5000)); // Warning: opening may cause irreversible ego damage
            productService.createProduct(new Product("Library Invitation (One-Way)", 999, 0)); // Free entry. No exit. No refunds.
            productService.createProduct(new Product("Lobotomy Corp Employee Starter Kit", 50, 199)); // Includes 1 clipboard, 2 aspirin, 0 hope
            productService.createProduct(new Product("Backstreets Survival Kit", 10, 850)); // Contains a rusty blade, trauma, and one stale ration bar
            productService.createProduct(new Product("Box of Aleph-Class Abnormalities", 1, 99999)); // Why is it humming. Why is it humming.
            productService.createProduct(new Product("Keter Clearance Badge", 5, 10000)); // For when you’re ready to cry at your desk at 9AM
            productService.createProduct(new Product("Distortion Fuel (Emotion Grade-5)", 2, 4200)); // Bottle your repression... then light it on fire
            productService.createProduct(new Product("The Seed of Light (Unstable)", 1, 999999)); // Hope is dangerous when manufactured

            productService.createProduct(new Product("‘Đù má, muốn chửi thề!’ Stress Ball", 10, 49)); // Squeeze when your code won’t compile. Or when life just be like that.
            productService.createProduct(new Product("CMD Color 4e cho máu", 5, 25)); // Adds +20 bloodlust to every terminal window.
            productService.createProduct(new Product("‘Màu Cờ Đỏ Sao Vàng’ Theme Pack", 3, 75)); // Makes your IDE patriotic and slightly more terrifying.
            productService.createProduct(new Product("‘Đẹp như Ngọc Trinh’ Desktop Wallpaper", 99, 0)); // Looks good... until runtime errors appear.
            productService.createProduct(new Product("pdfGenerator Locator Tool", 7, 120)); // Dependency Injection? Don’t sweat it. Let this find your missing beans.
            productService.createProduct(new Product("‘Đù má, chạy ngon chưa?’ Diagnostic Tuner", 8, 88)); // Auto-runs your app and screams if it fails.
            productService.createProduct(new Product("GiaoLang QA Contract: 'Không chạy ngon, không lấy tiền!'", 1, 199)); // 100% bug-free guarantee, or your next sprint is free.
            productService.createProduct(new Product("‘Đem bằng về cho mẹ’ Motivation Poster", 50, 30)); // Hang above your monitor. Cry silently while grinding.
            productService.createProduct(new Product("‘Cày ngày thêm hộc máu’ Coding Energy Drink", 20, 69)); // Now with 200mg caffeine, 1% regret, and 100% recursion.
            productService.createProduct(new Product("‘Mùa Noel Tốt Nghiệp’ Countdown Clock", 3, 2024)); // Ticks louder the closer December gets.


            log.info("Product initialization complete");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error while initializing product",e.getMessage());
        }
    }
}