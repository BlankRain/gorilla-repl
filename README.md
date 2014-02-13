# Gorilla REPL

Gorilla is a rich REPL for Clojure in the notebook style. What on earth does that mean, you say? Well, maybe it's best
to take a look at a short video showing you what it can do:

Video here.

You can think of it like a pretty REPL that can plot graphs, or you can think of it as an editor for rich documents that
can contain interactive Clojure code, graphs, table, notes, LaTeX formulae. Whatever works for you! One of the main
aims is to make it lightweight enough that you can use it day-to-day instead of the command-line REPL, but also offer
the power to perform and document complex data analysis and modelling tasks. Above all else, Gorilla tries not to
dictate your workflow, but rather to fit in to the way you like to work, hopefully putting a bit more power to your
elbow.

Screenshots here.


# Installation

The rest of these docs assume that you're familiar with the basics of Clojure, and have a working copy of Leiningen
(version >= 2) installed. If you're not yet familiar with Leiningen then you should head over to
[the Leiningen website](http://leiningen.org) and get it installed first. It's a really nice tool that makes things very
easy!

Gorilla is packaged as a Leiningen plugin. To use Gorilla you can do one of two things. If you just want to use Gorilla
in a particular project, then add the following to the :plugins section of that project's `project.clj` file:
```
[lein-gorilla "0.1.0-SNAPSHOT"]
```
Your completed `project.clj` file might look something like this:
```
(defproject gorilla-test "0.1.0-SNAPSHOT"
  :description "A test project for the Gorilla REPL."
  :dependencies [[org.clojure/clojure "1.5.1"]]
  :main ^:skip-aot gorilla-test.core
  :target-path "target/%s"
  :plugins [[lein-gorilla "0.1.0-SNAPSHOT"]]
  :profiles {:uberjar {:aot :all}})
```
The other way to use Gorilla is to add it to your Leiningen user profile - this will make it available everywhere, even
outside of Leiningen projects. Your `~/.lein/project.clj` might look like:
```
{ :user {
    :plugins [[lein-gorilla "0.1.0-SNAPSHOT"]]
  }
}
```

That's it. You should now be able to run `lein gorilla` and get started.


# Usage

When you run `lein gorilla` it will start up the REPL server, and print a web-link to the console. Point your
web-browser at this link to get going (hint for Mac users: try ctrl-clicking the link). You can open as many browser
windows as you like with this link, each will get its own nREPL session to work in, but share the same nREPL instance
(in case you're not familiar with nREPL's terminology: this means all windows will share definitions etc, but each
window will separately keep track of which namespace you're working in - try it, you'll see it's quite natural).

Once you've got a web-browser pointed at Gorilla you can use it just like a REPL. Type some clojure code in, and hit
shift+Enter to evaluate it. The results are displayed below the code, along with any console output or errors that were
generated. Gorilla offers nREPL's autocomplete function, hit `ctrl+space` to see what nREPL has to suggest (or, you can use
`ctrl+g ctrl+a` if you're using Firefox, which steals `ctrl+space` for its own use,
[somewhat controversially](https://bugzilla.mozilla.org/show_bug.cgi?id=435164).)

## Plotting graphs

One of the most handy features of Gorilla is the ability to plot graphs. The library
[gorilla-plot](https://github.com/JonyEpsilon/gorilla-plot) is integrated into Gorilla and is always available without
explicitly including it as a dependency in your `project.clj`. Full documentation for gorilla-plot is available on
[its Github page](https://github.com/JonyEpsilon/gorilla-plot), but to get you started, let's give a short summary.

There are five functions that should cover many plotting needs. These are:

- `(list-plot data)` where data can either be a sequence of y-values, or a sequence of `(x y)` pairs.
- `(plot func [start end])` which will evaluate and plot `func` over the given range.
- `(histogram data)` where `data` is a list of values.
- `(bar-chart categories values)` where `categories` are the category names, and `values` their value.
- `(compose plot1 plot2 & more)` which tries to compose together the given plots. Note that composing a barchart with
other plots will give odd results, as it's not obvious how to compose category-scales.

These functions take many options, look at the [gorilla-plot](https://github.com/JonyEpsilon/gorilla-plot) page for more
help.

There's a short video talking a little more about how the plots work, and how they try and fit nicely with the Clojure
way of thinking (plots are values) which might interest you.

Video here.

## Making notes

As well as including snippets of Clojure code, you can add snippets of notes to a Gorilla worksheet. These are added in
Markdown format. To add notes you need to first tell Gorilla that it should interpret a snippet of text as notes, rather
than Clojure code. To do this place the cursor in the segment (a segment being one of the boxes containing a snippet of
code/notes) that you want to use for notes and hit, `ctrl+g ctrl+m` (g for Gorilla, m for Markdown). You can then feel
free to put any Markdown you like in there. The notes segments also support LaTeX formulae. To write a formula you
simply surround the latex code with $$, or @@ if you want the formula to appear inline. So for instance, the contents of
a Markdown segment could be:
```
This is an inline formula, @@\sin(x)@@, and this is on its own line:
$$\int_0^{2\pi}\sin^2(x) \textrm{d}x$$
```

## Worksheet files

You can save the contents of a window to a 'worksheet' file. This will include everything you see, the code, the output,
notes and mathematics, the lot. To save a file just hit `ctrl+g ctrl+s`. If you haven't already saved the file it will
prompt for a filename, which is given relative to the project/where you invoked `lein gorilla`. To load a file, use
`ctrl+g ctrl+l`. By convention, I often find it convenient to store my worksheets in a directory called `ws` at the root
of the project (alongside `src` etc) but you, of course, can store them wherever you want. A neat feature is that these
worksheet files are just plain Clojure files with some magic comments. This means it's really easy to interactively
develop your code in Gorilla, and then turn it into a library when it stabilises.

## Other editor commands

You can see all of the editor commands by hovering over the feint question mark in the top right hand corner of the
Gorilla window. Hopefully they are all self-explanatory, or at least you can figure them out easily enough.

## doc, source and other REPL commands

You might be used to using `doc` and `source` at the command-line REPL. By default these are not imported into the
`user` namespace when Gorilla starts, but if you'd like to use them then you just need to run `(use 'clojure.repl)` to
bring them into scope.

Gorilla provides a few repl commands of its own. Again, these are not imported by default ... TODO.

## Content types

Copyright © 2014- Jony Hudson
