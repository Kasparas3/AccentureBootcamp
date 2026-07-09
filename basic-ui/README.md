# Shelter Starter

## Purpose

This project is a simple sample application for learning basic UI development with Java and Spring Boot. It is intended for coursework, experimentation, and local practice.

## Author

Created and maintained by Elina Rostoka, with development assistance from GitHub Copilot.

## Disclaimer

This repository contains a sample homework project created for an educational course on basic UI development. It is shared for learning purposes, does not represent an official employer product or service, and does not contain proprietary or confidential materials.

## Animal Images

The project supports local image files from static resources.

- Add custom animal photos to `src/main/resources/static/images/animals/`
- Store only the filename in the animal field, for example `Luna.jpeg`
- The app resolves that value to `/images/animals/Luna.jpeg`
- A full `http://` or `https://` image URL is also accepted and used as-is (see the seeded Rabbit/Pepper entry for a working example).

If image filename/url is missing, a fallback is selected by animal type:

- CAT -> `/images/fallback/fallback-cat.jpg`
- DOG -> `/images/fallback/fallback-dog.jpg`
- OTHER -> `/images/fallback/fallback-other.jpg`

## Security

The app is secured with Spring Security (see `SecurityConfig`). Two demo
accounts are provisioned in memory — passwords are intentionally simple for
coursework, not production use. Roles are deliberately **not** hierarchical:
an admin does not also hold `USER`, and vice versa.

| Username | Password  | Roles   |
|----------|-----------|---------|
| `user`   | `user123` | `USER`  |
| `admin`  | `admin123`| `ADMIN` |

- Browsing animal pages and reading the API (`GET`) is open to everyone.
- Creating an animal (`POST /animals`, `POST /api/animals`) requires `ADMIN`.
- Adopting an animal (`POST /api/animals/{id}/adopt`) requires `USER` —
  admins cannot adopt.
- Adopting sets the animal's `adoptionDetails` (adopter user ID + date) and
  flips its status to `ADOPTED`. Only `ADMIN` callers see the resulting
  `adoptionNote` ("adopted by {userId} on {date}") in the API response;
  everyone else just sees the bare `ADOPTED` status.
- Unauthenticated requests to a protected page are redirected to Spring
  Security's built-in default login page at `/login`.

**Bonus tasks** (see the instructions in `SecurityConfig`):
- **Web:** build your own login page instead of using the default one.
- **API/Swagger:** replace HTTP Basic on `/api/**` with JWT auth, so Swagger
  UI (Task B) can authorize once with a bearer token instead of prompting
  for credentials on every call.