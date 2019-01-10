(ns masken.main
  (:require [rum.core :as rum]
            [masken.view.app-component :refer [app-component]]))

(defonce state-atom (atom {}))

(defn main!
      []
      (println "Hej!"))

(defn render
      []
      (rum/mount (app-component) (js/document.getElementById "app")))

(defn on-reload
      {:dev/after-load true}
      []
      (render))