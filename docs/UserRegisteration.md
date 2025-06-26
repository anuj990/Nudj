### User Registration (Normal Users):

1. **Login Flow:**

   * When a user logs in, the app checks if the email is linked to any existing club.
   * If **not**, the app checks whether the email matches a student email pattern.

     * If it **matches**, the app may auto-classify them as a normal user or prompt for confirmation.
     * If it **doesn't match**, the user is given a choice to proceed as a **club** or **normal user**.

2. **Initial Registration Fields:**

   * **First Name** – Required
   * **Last Name** – Required
   * **Email ID** – Auto-fetched from Firebase Auth, also stored explicitly
   * **Branch** – Required; options:

     * Computer Science (CSE)
     * Electronics and Communication (EC)
     * Mechanical
     * Smart Manufacturing
     * Bachelor of Design (BDes)
   * **Batch** – Required (year like 2024, 2025, etc.)
   * **Profile Picture URL** – Optional

3. **Profile Fields (Editable Later):**

   * **Bio** – Optional text field, editable from the user’s profile page
   * **Phone Number** – Optional, editable from the user’s profile page

4. **Design Note:**

   * The registration process collects only essential information upfront.
   * Additional profile info like `bio` and `phone number` can be added or updated later for personalization.

