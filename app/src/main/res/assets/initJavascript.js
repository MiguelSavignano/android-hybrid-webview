(function() {
  try {
    var $ = document.querySelector.bind(document);

    Android.showToast("Hello world!!!");

    $("header") && $("header").remove();
    $("footer") && $("footer").remove();

    // var buttonAuth = $(".body .login > button");
    // var newButtonAuth = removeAllEventListeners(buttonAuth);
    // newButtonAuth.addEventListener("click", function(event) {
    //   Android.showToast("Override click action");
    // });
  } catch (err) {
    console.error(err);
  }

  function removeAllEventListeners(oldElement) {
    var newElement = oldElement.cloneNode(true);
    oldElement.parentNode.replaceChild(newElement, oldElement);
    return newElement;
  }
})();
