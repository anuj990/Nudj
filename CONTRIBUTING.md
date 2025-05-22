# Contributing to Nudj

Hey there, awesome human!
Welcome to **Nudj** – your one-stop app for never missing out on college events again. We're stoked to have you here and hope you’re ready to build something amazing with us. This guide will help you get started, stay on track, and make the most out of your open-source journey with us during **BSoC**.

Let's nudge productivity in the right direction.

---

## Quick Links

* [Getting Started](#getting-started)

    * [Issues](#issues)
    * [Pull Requests](#pull-requests)

        * [Fork & Clone](#fork--clone)
        * [Work on Pull Request](#work-on-pull-request)
        * [Keep Your Fork Updated](#keep-your-fork-updated)
* [Need Help?](#need-help)

---

## 🛠 Getting Started

We’re keeping it structured but super chill.
You’ll contribute through **Issues** (bugs/features/UI tasks) and **Pull Requests** (your awesome solutions). But first:

* **Look before you leap**: Check if an Issue or PR already exists before making a new one.
* **One change = One PR**: Keep things neat and focused.
* **Respect time**: We’ll try to review quickly, but life happens — a friendly ping always helps!

---

### Issues

Issues = To-do list + Idea board + Bug tracker.
You can:

* Report bugs 🐛
* Suggest features 💡
* Ask questions or share thoughts 🧠

You’ll see templates guiding you to share helpful info. If someone’s already reported the same thing, join the convo instead of making duplicates!

---

### Pull Requests

Pull Requests = Your playground to show us what you’ve got!

A good PR:

* Solves one thing cleanly ✅
* Follows the template 📋
* Doesn’t mix style fixes + big features 🙅‍♂️

Start small – we love seeing your creative takes on UI components, and then we’ll dive deeper into logic and Firebase magic later!

---

#### Fork & Clone

Let’s get your copy of Nudj up and running.

```bash
# Fork the repo on GitHub, then:
git clone https://github.com/YOUR-USERNAME/Nudj.git
cd Nudj
git remote add upstream https://github.com/bsoc-bitbyte/Nudj.git
git remote -v
```

✅ `origin` points to *your* fork.
✅ `upstream` points to *us*.

---

#### Work on Pull Request

```bash
# Create a new branch for your work
git checkout -b cool-feature

# Make your changes (code, test, repeat)
git add .
git commit -m "Add: Sparkly new feature"

# Push to your fork
git push origin cool-feature
```

Now head to GitHub, hit “Compare & pull request,” fill out the PR template, and boom — you're in the game!

Need to fix something? Just push to the same branch and GitHub will auto-update the PR.

**Golden Rule**: Never commit directly to `main` in your fork. Keep it squeaky clean

---

#### Keep Your Fork Updated

Don’t let your fork fall behind! Stay synced with us like this:

```bash
git checkout main
git pull upstream main
git push origin main
```

Repeat weekly or before starting new work!

---

## Need Help?

We gotchu 💬
Join the fun on our [BSoC Discord](https://discord.gg/wsTZBRMqdg) and ask away in the correct channel.

Just tag your message clearly and someone will swoop in to help.

---

## Final Nudj

* Don’t forget to read the [README.md](./README.md) – it’s where the magic begins ✨
* Ready to roll? Let’s ship it together. 🔥

---

👾 Built with 💙 by Team Nudj
Let’s nudge things in the right direction!

