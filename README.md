# Generative Design in Clojure

My attempt to learn [Clojure][] (and [Processing][]!) by going through
the code of [Generative Design][gd] and porting them to Clojure with
[Quil][quil].

I'll try to keep it as similar to the original as possible, for easy
reference (and progress :P). So if you're looking for mind-blowing
Clojure code, you might want to look somewhere else. 

When I've finished porting all of them (or when I'm confident enough),
I'll probably go through each one again and write more elegant versions
of each. (But that will be a *long* ways off.)

## Usage

    $ lein run `part` `chapter`
	# e.g.
	# to run the sketch in generative_design_clojure/principles/P_1_0_01
	$ lein run principles P_1_0_01

See [Quil's][quil] page for more info.

[quil]: https://github.com/quil/quil
[gd]: http://www.generative-gestaltung.de/code
[Processing]: http://processing.org
[Clojure]: http://clojure.org

## Progress

### P./// Basic Principles
#### P.1 Colour
1. [**`P_1_0_01`**](https://github.com/john2x/generative-design-clojure/blob/master/src/generative_design_clojure/principles/P_1_0_01/P_1_0_01.clj) - added on 2012-08-29
2. [**`P_1_1_1_01`**](https://github.com/john2x/generative-design-clojure/blob/master/src/generative_design_clojure/principles/P_1_1_1_01/P_1_1_1_01.clj) - added on 2012-08-30
3. [**`P_1_1_2_01`**](https://github.com/john2x/generative-design-clojure/blob/master/src/generative_design_clojure/principles/P_1_1_2_01/P_1_1_2_01.clj) - added on 2012-08-30
4. [**`P_1_2_1_01`**](https://github.com/john2x/generative-design-clojure/blob/master/src/generative_design_clojure/principles/P_1_2_1_01/P_1_2_1_01.clj) - added on 2012-09-01

## License

The same license as the ones found in Generative Design's code. See below.

    Generative Gestaltung, ISBN: 978-3-87439-759-9
    First Edition, Hermann Schmidt, Mainz, 2009
    Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
    Copyright 2009 Hartmut Bohnacker, Benedikt Gross, Julia Laub, Claudius Lazzeroni
    
    http://www.generative-gestaltung.de
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

