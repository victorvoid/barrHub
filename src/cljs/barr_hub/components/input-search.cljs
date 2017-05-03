(ns barr-hub.components.inputs
  (:require [re-frame.core :as re-frame]))

(defn redirect! [loc]
  (set! (.-location js/window) loc))

(defn input-search []
  (let [value (re-frame/subscribe [:search-key])]
    (fn []
      [:form
       [:input { :name "search"
                :placeholder "Search GitHub"
                :type "search"
                :class "search__input"
                :required ""
                :value @value
                :on-change #(re-frame/dispatch [:search-key (-> % .-target .-value)])}]
       [:button {
                 :type "Submit"
                 :class "btn btn__state--default"
                 :on-click #(do
                              (.preventDefault %)
                              (redirect! (str "/#/search/?q=" @value)))} "Submit"]])))
