QuoteVault 📱
A modern quote discovery & collection app

QuoteVault is a clean and modern mobile application built using Kotlin and Jetpack Compose.
The app focuses on providing a smooth user experience for discovering, saving, and organizing inspirational quotes, with special attention to UI quality, animations, and clean code structure.

This project was developed as part of the Mobile Application Developer Assignment.

✨ Overview
The goal of this assignment was to build a full-featured quote app while effectively using AI tools to speed up development and maintain clean, readable code.
In this submission, the frontend (UI/UX) is the primary focus, with backend integration planned but not yet connected.

✅ Features Implemented (Frontend)
🔐 Authentication (UI Only)
Login screen

Signup screen

Forgot password screen

Smooth animated navigation between authentication screens

Authentication logic is currently mocked. Backend integration is planned.

🏠 Quote Browsing & Discovery
Home screen with quote cards

Browse quotes by categories:

Motivation

Love

Success

Wisdom

Humor

Search quotes by text or author

Quote of the Day (local logic)

Custom pull-to-refresh interaction

Proper empty state handling

❤️ Favorites
Add or remove quotes from favorites

Dedicated favorites screen

Animated list with empty state UI

📂 Collections (Frontend)
Create custom collections

View list of collections

Collection detail screen

Add or remove quotes from a collection

Empty state handling

Collections are currently stored locally (frontend only).

📤 Sharing
Share quotes as text using the system share sheet

🎨 UI & UX Highlights
Fully custom Jetpack Compose UI

Animated background using Canvas

Consistent color palette and typography

Reusable composable components

Smooth transitions and micro-animations

🚧 Features Not Implemented Yet
The following features are not implemented yet and are intentionally documented as per assignment guidelines:

Supabase authentication and database integration

Cloud sync for favorites and collections

Push notifications for daily quotes

Quote card image generation and export

Settings screen (dark mode, font size, themes)

User profile screen

Home screen widget

🧠 AI Tools & Workflow
This project was built with strong support from AI-assisted development tools.

AI Tools Used
ChatGPT – UI planning, Jetpack Compose layouts, and architecture guidance

AI-assisted debugging – resolving animation and state issues

Prompt-driven development – breaking features into small, testable UI components

How AI Helped
Faster UI scaffolding

Cleaner and more readable Compose code

Improved animation patterns

Reduced development time while maintaining quality

🎨 Design Approach
UI designed directly in Jetpack Compose

Focus on:

Professional and modern look

Consistent spacing and typography

Smooth animations instead of default UI

Custom animated background implemented using Canvas and infinite transitions

🛠 Tech Stack
Language: Kotlin

UI Framework: Jetpack Compose

Architecture: Composable-based modular structure

State Management: remember / mutableState

Animations: Jetpack Compose Animations & Canvas

Backend: Planned (Supabase), not yet integrated
