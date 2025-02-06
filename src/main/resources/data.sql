-- Insertar datos de ejemplo para 'roles'
INSERT IGNORE INTO roles (id, name) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MANAGER'),
(3, 'ROLE_USER');

-- Insertar datos de ejemplo para 'users'. La contraseña de cada usuario es password
INSERT IGNORE INTO users (id, username, password, enabled, first_name, last_name, image, created_date, last_modified_date, last_password_change_date) VALUES
(1, 'admin', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Admin', 'User', '/images/admin.jpg', NOW(), NOW(), NOW()),
(2, 'manager', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Manager', 'User', '/images/manager.jpg', NOW(), NOW(), NOW()),
(3, 'normal', '$2b$12$FVRijCavVZ7Qt15.CQssHe9m/6eLAdjAv0PiOKFIjMU161wApxzye', true, 'Regular', 'User', '/images/user.jpg', NOW(), NOW(), NOW());

-- Asignar el rol de administrador al usuario con id 1
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(1, 1);
-- Asignar el rol de gestor al usuario con id 2
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(2, 2);
-- Asignar el rol de usuario normal al usuario con id 3
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES
(3, 3);

INSERT IGNORE INTO champions (code, name, rol) VALUES
("A1X2Y3", "Aatrox", "Fighter"),
("B4V5W6", "Ahri", "Mage"),
("C7Z8P9", "Alistar", "Tank"),
("D0Q1R2", "Annie", "Mage"),
("E3U4S5", "Ashe", "Marksman"),
("F6T7V8", "Blitzcrank", "Tank"),
("G9A0B1", "Brand", "Mage"),
("H1U2J3", "Braum", "Tank"),
("I4V5W6", "Caitlyn", "Marksman"),
("J7X8Y9", "Camille", "Fighter"),
("K0A1B2", "Corki", "Marksman"),
("L3C4D5", "Darius", "Fighter"),
("M6E7F8", "Draven", "Marksman"),
("N9G0H1", "Ekko", "Assassin"),
("P1Q2R3", "Fiora", "Fighter"),
("S4T5U6", "Galio", "Tank"),
("V7W8X9", "Garen", "Fighter"),
("Y0Z1A2", "Gnar", "Fighter"),
("B3C4D5", "Gragas", "Tank"),
("E6F7G8", "Hecarim", "Jungler"),
("H9I0J1", "Heimerdinger", "Mage"),
("L1M2N3", "Illaoi", "Fighter"),
("O4P5Q6", "Irelia", "Fighter"),
("R7S8T9", "Jhin", "Marksman"),
("U0V1W2", "Jinx", "Marksman"),
("X3Y4Z5", "Kai'Sa", "Marksman"),
("A6B7C8", "Kennen", "Mage"),
("D9E0F1", "Kha'Zix", "Assassin"),
("G2H3I4", "Kindred", "Marksman");

INSERT IGNORE INTO abilities (code, name, type, cost, cooldown, champion_id) VALUES
("AatroxQ", "The Darkin Blade", "Physical", 50, 20, (SELECT id FROM champions WHERE name = 'Aatrox')),
("AatroxW", "Infernal Chains", "Control", 60, 15, (SELECT id FROM champions WHERE name = 'Aatrox')),
("AatroxE", "Umbral Dash", "Mobility", 40, 12, (SELECT id FROM champions WHERE name = 'Aatrox')),
("AatroxR", "World Ender", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Aatrox')),

("AhriQ", "Orb of Deception", "Magic", 60, 13, (SELECT id FROM champions WHERE name = 'Ahri')),
("AhriW", "Fox-Fire", "Magic", 50, 10, (SELECT id FROM champions WHERE name = 'Ahri')),
("AhriE", "Charm", "Control", 70, 15, (SELECT id FROM champions WHERE name = 'Ahri')),
("AhriR", "Spirit Rush", "Ultimate", 100, 90, (SELECT id FROM champions WHERE name = 'Ahri')),

("AlistarQ", "Pulverize", "Control", 50, 15, (SELECT id FROM champions WHERE name = 'Alistar')),
("AlistarW", "Headbutt", "Physical", 60, 10, (SELECT id FROM champions WHERE name = 'Alistar')),
("AlistarE", "Trample", "Physical", 40, 14, (SELECT id FROM champions WHERE name = 'Alistar')),
("AlistarR", "Unbreakable Will", "Ultimate", 100, 110, (SELECT id FROM champions WHERE name = 'Alistar')),

("AnnieQ", "Disintegrate", "Magic", 50, 12, (SELECT id FROM champions WHERE name = 'Annie')),
("AnnieW", "Incinerate", "Magic", 60, 10, (SELECT id FROM champions WHERE name = 'Annie')),
("AnnieE", "Molten Shield", "Defensive", 40, 15, (SELECT id FROM champions WHERE name = 'Annie')),
("AnnieR", "Tibbers", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Annie')),

("AsheQ", "Ranger's Focus", "Buff", 40, 10, (SELECT id FROM champions WHERE name = 'Ashe')),
("AsheW", "Volley", "Physical", 50, 15, (SELECT id FROM champions WHERE name = 'Ashe')),
("AsheE", "Hawkshot", "Utility", 40, 20, (SELECT id FROM champions WHERE name = 'Ashe')),
("AsheR", "Enchanted Crystal Arrow", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Ashe')),

("BlitzcrankQ", "Rocket Grab", "Control", 60, 20, (SELECT id FROM champions WHERE name = 'Blitzcrank')),
("BlitzcrankW", "Overdrive", "Buff", 50, 15, (SELECT id FROM champions WHERE name = 'Blitzcrank')),
("BlitzcrankE", "Power Fist", "Physical", 40, 10, (SELECT id FROM champions WHERE name = 'Blitzcrank')),
("BlitzcrankR", "Static Field", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Blitzcrank')),

("BrandQ", "Sear", "Magic", 50, 12, (SELECT id FROM champions WHERE name = 'Brand')),
("BrandW", "Pillar of Flame", "Magic", 60, 15, (SELECT id FROM champions WHERE name = 'Brand')),
("BrandE", "Conflagration", "Magic", 70, 10, (SELECT id FROM champions WHERE name = 'Brand')),
("BrandR", "Fireball", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Brand'));

INSERT IGNORE INTO abilities (code, name, type, cost, cooldown, champion_id) VALUES
("BraumQ", "Winter's Bite", "Magic", 50, 14, (SELECT id FROM champions WHERE name = 'Braum')),
("BraumW", "Stand Behind Me", "Buff", 60, 20, (SELECT id FROM champions WHERE name = 'Braum')),
("BraumE", "Unbreakable", "Defensive", 70, 15, (SELECT id FROM champions WHERE name = 'Braum')),
("BraumR", "Glacial Fissure", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Braum')),

("CaitlynQ", "Piltover Peacemaker", "Physical", 50, 15, (SELECT id FROM champions WHERE name = 'Caitlyn')),
("CaitlynW", "Yordle Snap Trap", "Control", 60, 20, (SELECT id FROM champions WHERE name = 'Caitlyn')),
("CaitlynE", "90 Caliber Net", "Physical", 70, 15, (SELECT id FROM champions WHERE name = 'Caitlyn')),
("CaitlynR", "Ace in the Hole", "Ultimate", 100, 100, (SELECT id FROM champions WHERE name = 'Caitlyn')),

("CamilleQ", "Precision Protocol", "Physical", 50, 20, (SELECT id FROM champions WHERE name = 'Camille')),
("CamilleW", "Tactical Sweep", "Physical", 60, 14, (SELECT id FROM champions WHERE name = 'Camille')),
("CamilleE", "Hookshot", "Mobility", 70, 18, (SELECT id FROM champions WHERE name = 'Camille')),
("CamilleR", "The Hextech Ultimatum", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Camille')),

("CorkiQ", "Phosphorus Bomb", "Magic", 60, 10, (SELECT id FROM champions WHERE name = 'Corki')),
("CorkiW", "Valkyrie", "Magic", 70, 15, (SELECT id FROM champions WHERE name = 'Corki')),
("CorkiE", "Gatling Gun", "Physical", 80, 12, (SELECT id FROM champions WHERE name = 'Corki')),
("CorkiR", "Missile Barrage", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Corki')),

("DariusQ", "Decimate", "Physical", 40, 15, (SELECT id FROM champions WHERE name = 'Darius')),
("DariusW", "Crippling Strike", "Physical", 50, 10, (SELECT id FROM champions WHERE name = 'Darius')),
("DariusE", "Apprehend", "Physical", 60, 12, (SELECT id FROM champions WHERE name = 'Darius')),
("DariusR", "Noxian Guillotine", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Darius')),

("DravenQ", "Spinning Axes", "Physical", 50, 14, (SELECT id FROM champions WHERE name = 'Draven')),
("DravenW", "Blood Rush", "Buff", 40, 20, (SELECT id FROM champions WHERE name = 'Draven')),
("DravenE", "Stand Aside", "Control", 60, 18, (SELECT id FROM champions WHERE name = 'Draven')),
("DravenR", "Whirling Death", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Draven')),

("EkkoQ", "Timewinder", "Magic", 60, 15, (SELECT id FROM champions WHERE name = 'Ekko')),
("EkkoW", "Parallel Convergence", "Magic", 70, 18, (SELECT id FROM champions WHERE name = 'Ekko')),
("EkkoE", "Phase Dive", "Mobility", 50, 14, (SELECT id FROM champions WHERE code = 'Ekko')),
("EkkoR", "Chronobreak", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Ekko'));

INSERT IGNORE INTO abilities (code, name, type, cost, cooldown, champion_id) VALUES
("FioraQ", "Lunge", "Physical", 40, 8, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraW", "Bladework", "Buff", 50, 18, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraE", "Riposte", "Defensive", 60, 20, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraR", "Grand Challenge", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Fiora')),

("GalioQ", "Winds of War", "Magic", 60, 14, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioW", "Shield of Durand", "Defensive", 80, 20, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioE", "Justice Punch", "Physical", 70, 18, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioR", "Hero's Entrance", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Galio')),

("GarenQ", "Decisive Strike", "Physical", 40, 12, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenW", "Courage", "Buff", 60, 18, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenE", "Judgment", "Physical", 70, 15, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenR", "Demacian Justice", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Garen')),

("GnarQ", "Boomerang Throw", "Physical", 50, 14, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarW", "Hyper", "Buff", 60, 18, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarE", "Wallop", "Control", 70, 15, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarR", "GNAR!", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Gnar')),

("GragasQ", "Barrel Roll", "Magic", 60, 14, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasW", "Drunken Rage", "Buff", 70, 18, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasE", "Body Slam", "Physical", 80, 16, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasR", "Explosive Cask", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Gragas')),

("HecarimQ", "Rampage", "Physical", 40, 10, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimW", "Spirit of Dread", "Buff", 60, 15, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimE", "Devastating Charge", "Mobility", 70, 14, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimR", "Onslaught of Shadows", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Hecarim')),

("HeimerdingerQ", "H-28G Evolution Turret", "Magic", 50, 10, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerW", "CH-2 Electron Storm Grenade", "Control", 60, 15, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerE", "Uptimer", "Buff", 70, 20, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerR", "V-8 Evolution Turret", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Heimerdinger'));

INSERT IGNORE INTO abilities (code, name, type, cost, cooldown, champion_id) VALUES
("FioraQ", "Lunge", "Physical", 40, 8, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraW", "Bladework", "Buff", 50, 18, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraE", "Riposte", "Defensive", 60, 20, (SELECT id FROM champions WHERE name = 'Fiora')),
("FioraR", "Grand Challenge", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Fiora')),

("GalioQ", "Winds of War", "Magic", 60, 14, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioW", "Shield of Durand", "Defensive", 80, 20, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioE", "Justice Punch", "Physical", 70, 18, (SELECT id FROM champions WHERE name = 'Galio')),
("GalioR", "Hero's Entrance", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Galio')),

("GarenQ", "Decisive Strike", "Physical", 40, 12, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenW", "Courage", "Buff", 60, 18, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenE", "Judgment", "Physical", 70, 15, (SELECT id FROM champions WHERE name = 'Garen')),
("GarenR", "Demacian Justice", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Garen')),

("GnarQ", "Boomerang Throw", "Physical", 50, 14, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarW", "Hyper", "Buff", 60, 18, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarE", "Wallop", "Control", 70, 15, (SELECT id FROM champions WHERE name = 'Gnar')),
("GnarR", "GNAR!", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Gnar')),

("GragasQ", "Barrel Roll", "Magic", 60, 14, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasW", "Drunken Rage", "Buff", 70, 18, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasE", "Body Slam", "Physical", 80, 16, (SELECT id FROM champions WHERE name = 'Gragas')),
("GragasR", "Explosive Cask", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Gragas')),

("HecarimQ", "Rampage", "Physical", 40, 10, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimW", "Spirit of Dread", "Buff", 60, 15, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimE", "Devastating Charge", "Mobility", 70, 14, (SELECT id FROM champions WHERE name = 'Hecarim')),
("HecarimR", "Onslaught of Shadows", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Hecarim')),

("HeimerdingerQ", "H-28G Evolution Turret", "Magic", 50, 10, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerW", "CH-2 Electron Storm Grenade", "Control", 60, 15, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerE", "Uptimer", "Buff", 70, 20, (SELECT id FROM champions WHERE name = 'Heimerdinger')),
("HeimerdingerR", "V-8 Evolution Turret", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Heimerdinger')),

("IllaoiQ", "Tentacle Smash", "Physical", 60, 10, (SELECT id FROM champions WHERE name = 'Illaoi')),
("IllaoiW", "Harsh Lesson", "Buff", 50, 12, (SELECT id FROM champions WHERE name = 'Illaoi')),
("IllaoiE", "Test of Spirit", "Magic", 70, 18, (SELECT id FROM champions WHERE name = 'Illaoi')),
("IllaoiR", "Leap of Faith", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Illaoi')),

("IreliaQ", "Bladesurge", "Physical", 40, 12, (SELECT id FROM champions WHERE name = 'Irelia')),
("IreliaW", "Defiant Dance", "Buff", 60, 18, (SELECT id FROM champions WHERE name = 'Irelia')),
("IreliaE", "Flawless Duet", "Control", 70, 16, (SELECT id FROM champions WHERE name = 'Irelia')),
("IreliaR", "Vanguard's Edge", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Irelia')),

("JhinQ", "Whisper", "Physical", 50, 10, (SELECT id FROM champions WHERE name = 'Jhin')),
("JhinW", "Deadly Flourish", "Control", 60, 15, (SELECT id FROM champions WHERE name = 'Jhin')),
("JhinE", "Captive Audience", "Control", 70, 18, (SELECT id FROM champions WHERE name = 'Jhin')),
("JhinR", "Ultimate Showstopper", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Jhin')),

("JinxQ", "Switcheroo!", "Buff", 50, 12, (SELECT id FROM champions WHERE name = 'Jinx')),
("JinxW", "Zap!", "Magic", 70, 18, (SELECT id FROM champions WHERE name = 'Jinx')),
("JinxE", "Flame Chompers!", "Control", 60, 15, (SELECT id FROM champions WHERE name = 'Jinx')),
("JinxR", "Super Mega Death Rocket!", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Jinx')),

("KaisaQ", "Icathian Rain", "Physical", 40, 10, (SELECT id FROM champions WHERE name = "Kai'Sa")),
("KaisaW", "Void Seeker", "Magic", 60, 15, (SELECT id FROM champions WHERE name = "Kai'Sa")),
("KaisaE", "Supercharge", "Buff", 50, 20, (SELECT id FROM champions WHERE name = "Kai'Sa")),
("KaisaR", "Killer Instinct", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = "Kai'Sa")),

("KennenQ", "Thundering Shuriken", "Magic", 60, 10, (SELECT id FROM champions WHERE name = 'Kennen')),
("KennenW", "Electrical Surge", "Magic", 70, 15, (SELECT id FROM champions WHERE name = 'Kennen')),
("KennenE", "Lightning Rush", "Mobility", 50, 12, (SELECT id FROM champions WHERE name = 'Kennen')),
("KennenR", "Slicing Maelstrom", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Kennen')),

("KhaZixQ", "Taste Their Fear", "Physical", 50, 10, (SELECT id FROM champions WHERE name = "Kha'Zix")),
("KhaZixW", "Void Spike", "Healing", 60, 15, (SELECT id FROM champions WHERE name = "Kha'Zix")),
("KhaZixE", "Leap", "Mobility", 70, 12, (SELECT id FROM champions WHERE name = "Kha'Zix")),
("KhaZixR", "Void Assault", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = "Kha'Zix")),

("KindredQ", "Dance of Arrows", "Physical", 50, 10, (SELECT id FROM champions WHERE name = 'Kindred')),
("KindredW", "Wolf's Frenzy", "Magic", 60, 14, (SELECT id FROM champions WHERE name = 'Kindred')),
("KindredE", "Mounting Dread", "Control", 70, 16, (SELECT id FROM champions WHERE name = 'Kindred')),
("KindredR", "Lamb's Respite", "Ultimate", 100, 120, (SELECT id FROM champions WHERE name = 'Kindred'));
