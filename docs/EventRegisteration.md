### Event Registration (Admin Only):

1. **Access:**

   * Only club admins can create or edit events from their club dashboard.

2. **Event Data Fields:**

   * **Event ID**: Automatically generated unique identifier.
   * **Club ID**: ID of the club organizing the event.
   * **Submitted By**: Username or identifier of the club admin who registered the event.
   * **Event Name**: Required string.
   * **Event Description**: Required string.
   * **Event Banner URL**: Optional image URL for event promotion.
   * **Organizer Name**: String value.
   * **Organizer Contact Number**: String value.
   * **Event Dates & Durations**: List of entries, each containing:

     * **Start Date and Time**
     * **Estimated Duration**
   * **FAQs**: List of question-answer pairs to help attendees understand the event better.
   * **Filter Batch**: Optional list of batches (like 2022, 2023, etc.) to target specific student years.
   * **IsDeleted**: Boolean flag to support soft deletion (default: false).
   * **Creation Timestamp**: Auto-generated when the event is first created.
   * **Last Updated Timestamp**: Updated whenever the event is modified.
