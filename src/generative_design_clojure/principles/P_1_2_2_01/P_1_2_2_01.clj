; P_1_2_2_01.clj
;
; Licensed under the Apache License, Version 2.0

; shows how to interpolate colors in different styles/ color modes
;
; MOUSE
; position x    : resolution
;
; KEYS
; 1-3           : load different images
; 4             : no color sorting
; 5             : sort colors on hue
; 6             : sort colors on saturation
; 7             : sort colors on brightness
; 8             : sort colors on grayscale (lumincance)
; s             : save png

(ns generative-design-clojure.principles.P_1_2_2_01.P_1_2_2_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def data-folder "P_1_2_2_01/")

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (no-stroke)
  (set-state!
    :img (atom (load-image (str data-folder "pic1.jpg")))
    :sort-mode (atom nil)
    :colors (atom [])))

(defn draw []
  (let [tile-count (/ (width) (max (mouse-y) 5)),
        rect-size (/ (width) (float tile-count)),
        img @(state :img)
        colors (atom [])]

    ; get colors from image
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count)]
      (let [px (int (* grid-x rect-size)),
            py (int (* grid-y rect-size))]
        (swap! colors conj (.get img px py))))

    ; sort colors
    ; (when (not= @(state :sort-mode) nil)
    ;   (swap! colors
    ;          GenerativeDesign/sortColors
    ;          (current-applet) @colors @(state :sort-mode)))

    ; draw grid
    (doseq [[grid-y j] (indexed-range tile-count),
            [grid-x k] (indexed-range tile-count)
            :let [i (int (+ (* tile-count grid-y) k))]]
      (fill (@colors i))
      (rect (* grid-x rect-size) (* grid-y rect-size) rect-size rect-size))))

(defn key-release []
  (case (str (raw-key))
    "1" (reset! (state :img) (load-image (str data-folder "pic1.jpg")))
    "2" (reset! (state :img) (load-image (str data-folder "pic2.jpg")))
    "3" (reset! (state :img) (load-image (str data-folder "pic3.jpg")))

    "4" (reset! (state :sort-mode) nil)
    "5" (reset! (state :sort-mode) (GenerativeDesign/HUE))
    "6" (reset! (state :sort-mode) (GenerativeDesign/SATURATION))
    "7" (reset! (state :sort-mode) (GenerativeDesign/BRIGHTNESS))
    "8" (reset! (state :sort-mode) (GenerativeDesign/GRAYSCALE))
    nil))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defsketch P_1_2_2_01
           :title "P_1_2_2_01"
           :setup setup
           :draw draw
           :key-released key-release
           :size [600 600])
