### Follow Request Feature:

1. **Purpose:**

   * Allows a student (normal user) to follow a specific club and stay updated on its events or announcements.

2. **Data Fields:**

   * **Follow ID**: Unique identifier for each follow record.
   * **Student/User ID**: ID of the user who followed the club.
   * **Club ID**: ID of the club being followed.
   * **Follow Timestamp**: The exact date and time the follow action was performed.

3. **Functionality:**

   * A user can follow or unfollow a club.
   * The system stores the follow record and deletes it on unfollow.

---

### RSVP Feature:

1. **Purpose:**

   * Allows a student to RSVP (register interest) for a specific event.

2. **Data Fields:**

   * **RSVP ID**: Unique identifier for each RSVP record.
   * **Student/User ID**: ID of the user RSVPing to the event.
   * **Event ID**: ID of the event the user is RSVPing for.
   * **RSVP Timestamp**: The exact date and time the RSVP was made.

3. **Functionality:**

   * A user can RSVP or un-RSVP to any event.
   * RSVP records are created and deleted accordingly to reflect the user’s interest status.

