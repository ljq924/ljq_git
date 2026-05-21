---
name: "html-ppt"
description: "Create stunning HTML presentations using beautiful-html-templates (34 templates) or custom styles from frontend-slides. Invoke when user wants to build a presentation, convert PPT/PPTX to web, or create slides for a talk/pitch."
---

# HTML PPT — Beautiful Presentations

Create zero-dependency, animation-rich HTML presentations. Choose from **34 pre-built templates** (beautiful-html-templates) or **12 custom style presets** (frontend-slides) for maximum design flexibility.

## Resource Paths

All supporting files are located at:

- **Templates root:** `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\`
- **Frontend-slides root:** `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\`

When you need to read any file, use these absolute paths.

## Core Principles

1. **Zero Dependencies** — Single HTML files with inline CSS/JS. No npm, no build tools.
2. **Show, Don't Tell** — Generate visual previews, not abstract choices. People discover what they want by seeing it.
3. **Distinctive Design** — No generic "AI slop." Every presentation must feel custom-crafted.
4. **Viewport Fitting (NON-NEGOTIABLE)** — Every slide MUST fit exactly within 100vh. No scrolling within slides, ever. Content overflows? Split into multiple slides.

## Design Aesthetics

Avoid generic "AI slop" aesthetic. Focus on:

- **Typography:** Choose fonts that are beautiful, unique, and interesting. Avoid generic fonts like Arial and Inter.
- **Color & Theme:** Commit to a cohesive aesthetic. Use CSS variables for consistency. Dominant colors with sharp accents.
- **Motion:** Use animations for effects and micro-interactions. Prioritize CSS-only solutions.
- **Backgrounds:** Create atmosphere and depth rather than defaulting to solid colors.

Avoid:
- Overused font families (Inter, Roboto, Arial, system fonts)
- Cliched color schemes (particularly purple gradients on white backgrounds)
- Predictable layouts and component patterns

## Viewport Fitting Rules

These invariants apply to EVERY slide in EVERY presentation:

- Every `.slide` must have `height: 100vh; height: 100dvh; overflow: hidden;`
- ALL font sizes and spacing must use `clamp(min, preferred, max)` — never fixed px/rem
- Content containers need `max-height` constraints
- Images: `max-height: min(50vh, 400px)`
- Breakpoints required for heights: 700px, 600px, 500px
- Include `prefers-reduced-motion` support
- Never negate CSS functions directly — use `calc(-1 * clamp(...))` instead

**When generating from scratch (Mode B), read `viewport-base.css` and include its full contents in every presentation.**

### Content Density Limits Per Slide

| Slide Type    | Maximum Content                                           |
| ------------- | --------------------------------------------------------- |
| Title slide   | 1 heading + 1 subtitle + optional tagline                 |
| Content slide | 1 heading + 4-6 bullet points OR 1 heading + 2 paragraphs |
| Feature grid  | 1 heading + 6 cards maximum (2x3 or 3x2)                  |
| Code slide    | 1 heading + 8-10 lines of code                            |
| Quote slide   | 1 quote (max 3 lines) + attribution                       |
| Image slide   | 1 heading + 1 image (max 60vh height)                     |

---

## Phase 0: Detect Mode

Determine what the user wants:

- **Mode A: Template-Based Presentation** — Use a pre-built template from beautiful-html-templates. Go to Phase 1A.
- **Mode B: Custom Presentation** — Create from scratch using style presets. Go to Phase 1B.
- **Mode C: PPT Conversion** — Convert a .pptx file. Go to Phase 5.
- **Mode D: Enhancement** — Improve an existing HTML presentation. Read it, understand it, enhance. Follow Mode D modification rules below.

### How to decide between Mode A and Mode B

Ask the user (header: "Mode"):

- "Use a pre-built template" (recommended) — 34 professionally designed templates, faster to produce, guaranteed visual quality
- "Design from scratch" — Full creative control using 12 style presets, more unique but takes longer

### Mode D: Modification Rules

When enhancing existing presentations, viewport fitting is the biggest risk:

1. **Before adding content:** Count existing elements, check against density limits
2. **Adding images:** Must have `max-height: min(50vh, 400px)`. If slide already has max content, split into two slides
3. **Adding text:** Max 4-6 bullets per slide. Exceeds limits? Split into continuation slides
4. **After ANY modification, verify:** `.slide` has `overflow: hidden`, new elements use `clamp()`, images have viewport-relative max-height, content fits at 1280x720
5. **Proactively reorganize:** If modifications will cause overflow, automatically split content and inform the user

---

## Phase 1A: Template-Based Workflow (beautiful-html-templates)

### Step 1A.1: Ask about occasion and mood

Ask (header: "Occasion"):
What is this presentation for? (e.g. founder pitch, research synthesis, brand manifesto, classroom kickoff, etc.)

Ask (header: "Mood"):
What mood / vibe do you want? (e.g. confident & punchy, quiet & literary, warm & playful, dark & moody, etc.)

Ask (header: "Length"):
Approximately how many slides? Options: Short 5-10 / Medium 10-20 / Long 20+

Ask (header: "Content"):
Do you have content ready? Options: All content ready / Rough notes / Topic only

Wait for the user's answer. Do not pick yet.

### Step 1A.2: Read index.json and pick 3 candidates

Read `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\index.json`. Match the user's stated occasion + mood against each template's `mood`, `tone`, `best_for`, and `formality`. **Pick three templates** whose tones genuinely fit. The three should be *different enough from each other* that the user has a real choice.

Field definitions for index.json:

| field | how to use it |
|---|---|
| `mood` | emotional adjectives. Match against the user's *feeling* keywords. |
| `occasion` | example use cases. Useful as soft signal, not a hard filter. |
| `tone` | voice / personality. Match against descriptors like "playful", "serious", "literary". |
| `formality` | `low` / `medium-low` / `medium` / `medium-high` / `high`. Sanity-check against the user's audience. |
| `density` | how much content per slide the template can hold. Match against the user's content volume. |
| `scheme` | `light` / `dark` / `mixed`. Hard signal if the user explicitly wants light or dark. |
| `best_for` | the **feeling** + example contexts. Lead with this when narrating your pick. |
| `avoid_for` | tone *clashes* — soft warning, not a veto. |
| `slide_count` | size of the demo deck. Hint for how many layouts the template demonstrates. |

Tone-first matching: Templates have **tones**, not industries. The user's taste wins. Lead with `mood` + `tone` + `best_for`. Treat `avoid_for` as a soft warning. Use `formality` and `density` to sanity-check.

### Step 1A.3: Build title-slide preview of each candidate

For each of the 3 candidates:

1. Read the template's `template.html` from `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\templates\<slug>\template.html`
2. Also read the template's `design.md` for design system details
3. Take the **first slide only** (the cover / title slide of that template)
4. Replace the placeholder content with **the user's actual deck topic / title / subtitle / author / date**
5. Save the preview as a standalone HTML file in a temp folder, e.g. `.claude-design/slide-previews/01-<slug>.html`. Keep all sibling assets the template needs.

### Step 1A.4: Open all 3 previews, let user pick

Open each preview in the browser. Then ask (header: "Template"):

Which template do you prefer? Options: Template A: [Name] / Template B: [Name] / Template C: [Name] / Mix elements

Wait for the user to pick.

### Step 1A.5: Build the full deck in the chosen template

Once the user picks:

1. Clone the chosen template's full folder from `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\templates\<slug>\` into the user's project workspace.
2. Adapt every slide per the template adaptation rules below.
3. If the user's deck needs more slides than the template's demo holds, duplicate existing layouts to fit; if it needs fewer, drop slides from the bottom. Update page-number labels.
4. **If a slide needs a layout the template doesn't have, design it from scratch using the template's design system** — same fonts, same color palette, same decorative vocabulary, same spacing rhythm, same component grammar.

### Template Adaptation Rules

**Always preserve (these ARE the design system):**

- **Fonts.** Whatever the template imports from Google Fonts or declares in `font-family` — keep it. Never substitute.
- **Color palette.** All `:root` CSS variables and color values. Never recolor.
- **Layout grid.** The grid columns, the absolute positioning, the flex hierarchies. Don't restructure.
- **Slide-level CSS classes** (e.g. `.s-toc`, `.slide--quote`, `.layout-cover`). These carry the visual identity.
- **Decorative elements** — corner brackets, paper grain, geometric shapes, illustrated SVGs, hand-drawn doodles.
- **The navigation runtime** — whether it's `deck-stage.js`, an inline keyboard handler, scroll-snap, or nothing.

**Always replace (this is the user's content):**

- **Headlines** — `<h1>`, `<h2>`, `<h3>`, etc.
- **Body copy** — `<p>`, list items, captions.
- **Numbers and stats** — placeholder values like `47%`, `2.4M`, `+142%`.
- **Names, dates, attributions** — author names, citation lines, placeholder tokens.
- **Image placeholders** — replace with the user's real image, **at the same dimensions**.

**Designing missing layouts (extending a template):**

If the user needs a slide layout the template doesn't include, design it using the template's existing design system:

- Same fonts, same color palette, same decorative vocabulary, same spacing rhythm, same component grammar, same chrome (top label / bottom page-number / corner mark), same navigation behavior.
- A good test: open your new slide between two existing slides. If it visibly *belongs*, you've succeeded.

**Common pitfalls:**

- Don't substitute fonts. "Inter is similar enough" — no, the typography is the design system.
- Don't recolor. Even small accent shifts break the palette's harmony.
- Don't combine layouts from different templates. Each template is a closed visual system.
- Don't strip "extra" decoration thinking it's noise. Corner brackets, paper grain, SVG ornaments — they are part of the identity.
- Don't try to "modernize" old templates — they're working as designed.

---

## Phase 1B: Custom Presentation Workflow (frontend-slides)

### Step 1B.1: Content Discovery

Ask ALL questions in a single AskUserQuestion call:

**Question 1 — Purpose** (header: "Purpose"):
What is this presentation for? Options: Pitch deck / Teaching-Tutorial / Conference talk / Internal presentation

**Question 2 — Length** (header: "Length"):
Approximately how many slides? Options: Short 5-10 / Medium 10-20 / Long 20+

**Question 3 — Content** (header: "Content"):
Do you have content ready? Options: All content ready / Rough notes / Topic only

**Question 4 — Inline Editing** (header: "Editing"):
Do you need to edit text directly in the browser after generation? Options:
- "Yes (Recommended)" — Can edit text in-browser, auto-save to localStorage, export file
- "No" — Presentation only, keeps file smaller

If user has content, ask them to share it.

### Step 1B.2: Image Evaluation (if images provided)

If user selected "No images" → skip to Phase 2B.

If user provides an image folder:
1. Scan — List all image files
2. View each image — Use the Read tool
3. Evaluate — For each: what it shows, USABLE or NOT USABLE, what concept it represents, dominant colors
4. Co-design the outline — Curated images inform slide structure alongside text
5. Confirm via AskUserQuestion (header: "Outline"): "Does this slide outline and image selection look right?"

---

## Phase 2B: Style Discovery (Custom Mode)

### Step 2B.0: Style Path

Ask (header: "Style"):
- "Show me options" (recommended) — Generate 3 previews based on mood
- "I know what I want" — Pick from preset list directly

**If direct selection:** Show preset picker and skip to Phase 3B. Available presets are defined in `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\STYLE_PRESETS.md`.

### Step 2B.1: Mood Selection

Ask (header: "Vibe", multiSelect: true, max 2):
What feeling should the audience have? Options:
- Impressed/Confident — Professional, trustworthy
- Excited/Energized — Innovative, bold
- Calm/Focused — Clear, thoughtful
- Inspired/Moved — Emotional, memorable

### Step 2B.2: Generate 3 Style Previews

Based on mood, generate 3 distinct single-slide HTML previews. Read `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\STYLE_PRESETS.md` for available presets and their specifications.

| Mood                | Suggested Presets                                  |
| ------------------- | -------------------------------------------------- |
| Impressed/Confident | Bold Signal, Electric Studio, Dark Botanical       |
| Excited/Energized   | Creative Voltage, Neon Cyber, Split Pastel         |
| Calm/Focused        | Notebook Tabs, Paper & Ink, Swiss Modern           |
| Inspired/Moved      | Dark Botanical, Vintage Editorial, Pastel Geometry |

Save previews to `.claude-design/slide-previews/` (style-a.html, style-b.html, style-c.html).

### Step 2B.3: User Picks

Ask (header: "Style"):
Which style preview do you prefer? Options: Style A: [Name] / Style B: [Name] / Style C: [Name] / Mix elements

---

## Phase 3B: Generate Custom Presentation

Generate the full presentation using content from Phase 1B and style from Phase 2B.

**Before generating, read these supporting files:**

- `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\html-template.md` — HTML architecture and JS features
- `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\viewport-base.css` — Mandatory CSS (include in full)
- `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\animation-patterns.md` — Animation reference for the chosen feeling

**Key requirements:**

- Single self-contained HTML file, all CSS/JS inline
- Include the FULL contents of viewport-base.css in the `<style>` block
- Use fonts from Fontshare or Google Fonts — never system fonts
- Add detailed comments explaining each section
- Every section needs a clear `/* === SECTION NAME === */` comment block

---

## Phase 5: PPT Conversion

When converting PowerPoint files:

1. **Extract content** — Run `python f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\extract-pptx.py <input.pptx> <output_dir>` (install python-pptx if needed: `pip install python-pptx`)
2. **Confirm with user** — Present extracted slide titles, content summaries, and image counts
3. **Style selection** — Ask user whether to use a template (Phase 1A) or custom style (Phase 2B)
4. **Generate HTML** — Convert to chosen style, preserving all text, images, slide order, and speaker notes

---

## Phase 6: Delivery

1. **Clean up** — Delete `.claude-design/slide-previews/` if it exists
2. **Open** — Open the finished HTML file in the browser
3. **Summarize** — Tell the user:
   - File location, style/template name, slide count
   - Navigation: Arrow keys, Space, scroll/swipe, click nav dots
   - How to customize: `:root` CSS variables for colors, font link for typography, `.reveal` class for animations
   - If inline editing was enabled: Hover top-left corner or press E to enter edit mode, click any text to edit, Ctrl+S to save

---

## Phase 7: Share & Export (Optional)

After delivery, ask: _"Would you like to share this presentation? I can deploy it to a live URL or export it as a PDF."_

Options: Deploy to URL / Export to PDF / Both / No thanks

### 7A: Deploy to a Live URL (Vercel)

1. Check if Vercel CLI is installed — Run `npx vercel --version`
2. Check if user is logged in — Run `npx vercel whoami`
3. Deploy — Run: `bash f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\deploy.sh <path-to-presentation>`
4. Share the URL

### 7B: Export to PDF

1. Run: `bash f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\export-pdf.sh <path-to-html> [output.pdf]`
2. If Playwright installation fails: `npx playwright install chromium`
3. For large PDFs (>10MB), re-run with `--compact` flag

---

## Supporting Files Reference

| File | Path | Purpose | When to Read |
|------|------|---------|--------------|
| index.json | `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\index.json` | 34 template metadata for matching | Phase 1A.2 |
| Template files | `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\templates\<slug>\` | Template HTML, CSS, JS, design docs | Phase 1A.3, 1A.5 |
| AGENTS.md | `f:\zuomian\项目\html&ppt\beautiful-html-templates-main\beautiful-html-templates-main\AGENTS.md` | Template adaptation rules | Phase 1A.5 |
| STYLE_PRESETS.md | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\STYLE_PRESETS.md` | 12 curated visual presets | Phase 2B |
| viewport-base.css | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\viewport-base.css` | Mandatory responsive CSS | Phase 3B |
| html-template.md | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\html-template.md` | HTML structure, JS features | Phase 3B |
| animation-patterns.md | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\animation-patterns.md` | Animation snippets | Phase 3B |
| extract-pptx.py | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\extract-pptx.py` | PPT content extraction | Phase 5 |
| deploy.sh | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\deploy.sh` | Deploy to Vercel | Phase 7A |
| export-pdf.sh | `f:\zuomian\项目\html&ppt\frontend-slides-main\frontend-slides-main\scripts\export-pdf.sh` | Export to PDF | Phase 7B |
