/*
 * This file is part of gorilla-repl. Copyright (C) 2014, Jony Hudson.
 *
 * gorilla-repl is licenced to you under the MIT licence. See the file LICENCE.txt for full details.
 */

// Listens for "command:*" events and processes them, usually by firing off new events that are handled by the
// appropriate component.

var commandProcessor = (function () {

    var self = {};


    // so that our keyboard shortcuts work in the codeMirror textareas.
    Mousetrap.stopCallback = function () {
        return false
    };

    self.addCommand = function (command) {
        eventBus.on(command.name, command.action);
        if (command.kb) Mousetrap.bind(command.kb, function () {
            eventBus.trigger(command.name);
        });
    };

    return self;

})();

// The list of commands. These could be located with the components they belong too if the list gets too unwieldy,
// but for now they're fine together here.

[
    {
        name: "command:worksheet:leaveBack",
        desc: "Move to the previous segment.",
        kb: "up",
        action: function () {
            eventBus.trigger("worksheet:leaveBack");
        }
    },
    {
        name: "command:worksheet:leaveForward",
        desc: "Move to the next segment.",
        kb: "down",
        action: function () {
            eventBus.trigger("worksheet:leaveForward");
        }
    },
    {
        name: "command:worksheet:delete",
        desc: "Delete the active next segment.",
        kb: "ctrl+g ctrl+x",
        action: function () {
            eventBus.trigger("worksheet:delete");
        }
    },
    {
        name: "command:worksheet:newBelow",
        desc: "Create a new segment below the active segment.",
        kb: "ctrl+g ctrl+n",
        action: function () {
            eventBus.trigger("worksheet:newBelow");
        }
    },
    {
        name: "command:evaluator:evaluate",
        desc: "Evaluate the active segment.",
        kb: "shift+enter",
        action: function () {
            eventBus.trigger("worksheet:evaluate");
        }
    },
    {
        name: "command:worksheet:changeToFree",
        desc: "Convert the highlighted segment to a free segment.",
        kb: "ctrl+g ctrl+f",
        action: function () {
            eventBus.trigger("worksheet:changeToFree");
        }
    },
    {
        name: "command:worksheet:changeToCode",
        desc: "Convert the highlighted segment to a code segment.",
        kb: "ctrl+g ctrl+c",
        action: function () {
            eventBus.trigger("worksheet:changeToCode");
        }
    }
].forEach(commandProcessor.addCommand);