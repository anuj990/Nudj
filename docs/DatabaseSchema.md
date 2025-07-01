# Database Schema for Nudj (Firestore & Realtime DB)

## Overview
This schema is designed for Firebase Firestore (for structured, queryable data) and Firebase Realtime Database (for real-time chat/Q&A). It supports two user types: **Normal Users** (students) and **Clubs** (organizations). Both can log in, but clubs cannot access normal user features (like following, RSVPing, or reviewing events).

---

## 1. Firestore Collections

### users
- **Purpose:** Stores both normal users and clubs. Differentiated by `userType`.
- **Document ID:** `userId` (Firebase Auth UID)
- **Fields:**
  - `userType`: string ("normal" | "club")
  - `email`: string
  - `firstName`: string (normal users only)
  - `lastName`: string (normal users only)
  - `branch`: string (normal users only)
  - `batch`: string (normal users only)
  - `profilePictureUrl`: string (optional)
  - `bio`: string (optional, normal users only)
  - `phoneNumber`: string (optional, normal users only)
  - `clubName`: string (clubs only)
  - `clubLogo`: string (clubs only)
  - `clubCategory`: string (clubs only)
  - `additionalDetails`: string (optional, clubs only)
  - `description`: string (optional, clubs only)
  - `achievements`: string (optional, clubs only)


### events
- **Purpose:** Stores event details, created by clubs.
- **Document ID:** `eventId` (auto-generated)
- **Fields:**
  - `clubId`: string (userId of the club)
  - `eventName`: string
  - `eventDescription`: string
  - `eventBannerUrl`: string (optional)
  - `organizerName`: string
  - `organizerContactNumber`: string
  - `eventDates`: array of objects
    - `startDateTime`: timestamp
    - `estimatedDuration`: string
  - `faqs`: array of objects
    - `question`: string
    - `answer`: string
  - `filterBatch`: array of strings (optional)
  - `isDeleted`: boolean (default: false)
  - `creationTimestamp`: timestamp
  - `lastUpdatedTimestamp`: timestamp

---

### follows
- **Purpose:** Tracks which users follow which clubs.
- **Document ID:** `followId` (auto-generated)
- **Fields:**
  - `userId`: string (normal user)
  - `clubId`: string (club user)
  - `followTimestamp`: timestamp

---

### rsvps
- **Purpose:** Tracks which users RSVP to which events.
- **Document ID:** `rsvpId` (auto-generated)
- **Fields:**
  - `userId`: string (normal user)
  - `eventId`: string
  - `rsvpTimestamp`: timestamp

---

### reviews
- **Purpose:** Stores reviews/ratings for events, only by users who RSVPed and after event ends.
- **Document ID:** `reviewId` (auto-generated)
- **Fields:**
  - `userId`: string (normal user)
  - `clubId`: string
  - `eventId`: string
  - `rating`: number (1-5)
  - `review`: string (optional)
  - `createdAt`: timestamp

---

## 2. Realtime Database Structure (Event Q&A)

- **Purpose:** Real-time chat for event questions between users and clubs.
- **Structure:**
```json
event_questions: {
  [eventId]: {
    messages: {
      [messageId]: {
        senderId: string, // userId (normal or club)
        senderType: "normal" | "club",
        text: string,
        timestamp: number
      },
      ...
    }
  },
  ...
}
```

---

## 3. Relationships & Usage Notes
- All users (normal or club) are in the `users` collection, differentiated by `userType`.
- Only users with `userType: "normal"` can follow clubs, RSVP, or review events (enforced in app logic and security rules).
- Clubs create events and answer questions in Q&A.
- Use Firestore security rules to restrict actions based on `userType`.

---

**This schema is extensible for future features (e.g., notifications, admin roles, etc.).**