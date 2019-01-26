(ns masken.main
  (:require [rum.core :as rum]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [masken.core :as core]
            [masken.view.app-component :refer [app-component]]))

(defonce state-atom (atom {:title "Masken!"}))


(defn button-clicked-handler
      [{db :db} [_ id]]
      {:db (update-in db [id :counter] inc)})


(defn query-counter
      [db [_ id]]
      (get-in db [id :counter]))


(re-frame/reg-event-fx
  :button-clicked
  button-clicked-handler)


(re-frame/reg-event-fx
  :tick
  (fn [{db :db} _]
      {:db (update db :time inc)}))


(re-frame/reg-sub
  :query-counter
  query-counter)

(re-frame/reg-sub
  :query-time
  (fn [db _]
      (:time db)))

(re-frame/reg-sub :query-worm
                  (fn [db _]
                      (:worm db)))

(re-frame/reg-event-db
  :initialize
  (fn [_ _]
      (core/create-db)))

(re-frame/reg-event-fx :move
                       (fn [{db :db} [_ direction]]
                           (println "Sending move to db")
                           {:db (core/move-in-direction db direction)}))

(defn render
      []
      (reagent/render [app-component] (js/document.getElementById "app")))

(defn on-reload
      {:dev/after-load true}
      []
      (render))

(defn register-input!
      [element]
      (println element)
      (element.addEventListener "keydown"
                                (fn [e]
                                    (println "A move detected")
                                    (condp = (.-keyCode e)
                                           37 (re-frame/dispatch [:move :left])
                                           38 (re-frame/dispatch [:move :up])
                                           39 (re-frame/dispatch [:move :right])
                                           40 (re-frame/dispatch [:move :down])
                                           nil))))

(defn main!
      []
      (println "Running main")
      (re-frame/dispatch [:initialize])
      (render)
      (register-input! (.-body js/document))
      (js/setInterval (fn [] (re-frame/dispatch [:tick])) 1000))
