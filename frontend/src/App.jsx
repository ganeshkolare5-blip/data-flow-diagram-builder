import FormPage from "./pages/FormPage";
import ListPage from "./pages/ListPage";

function App() {
  return (
    <div className="container mx-auto max-w-4xl p-4">
      <FormPage />
      <hr className="my-8" />
      <ListPage />
    </div>
  );
}

export default App;
