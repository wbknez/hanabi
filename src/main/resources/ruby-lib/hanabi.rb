#!/usr/bin/env ruby
#
# Summary: Converts the core Hanabi API into a Ruby module.

require 'java'

# The following module represents the entirety of this project condensed to a
# single namespace.
# This is acceptable because none of the class names collide.
# This also helps make the project more accessible to scripting by locating
# all potential classes in the same space with a single name, unlike the
# standard RubyBridge style declaration(s).

module Hanabi
  include_package "com.solsticesquared.hanabi"
  include_package "com.solsticesquared.hanabi.math"
  include_package "com.solsticesquared.hanabi.render"
  include_package "com.solsticesquared.hanabi.render.point"
  include_package "com.solsticesquared.hanabi.ui"
end
