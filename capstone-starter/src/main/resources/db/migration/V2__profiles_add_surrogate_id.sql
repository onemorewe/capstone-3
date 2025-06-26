/* -----------------------------------------------------------------
   Add AUTO_INCREMENT surrogate key to profiles
   and keep user_id unique for existing FK references.
------------------------------------------------------------------ */
ALTER TABLE profiles
    ADD COLUMN id INT UNSIGNED NOT NULL AUTO_INCREMENT FIRST,
    DROP PRIMARY KEY,
    ADD PRIMARY KEY (id),
    ADD UNIQUE KEY uq_profiles_user (user_id);
