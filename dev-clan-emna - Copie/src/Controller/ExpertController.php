<?php

namespace App\Controller;

use App\Entity\Expert;
use App\Form\ExpertType;
use App\Form\SearchType;
use App\Repository\ExpertRepository;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/expert')]
class ExpertController extends AbstractController
{
    #[Route('/', name: 'app_expert_index', methods: ['GET'])]
    public function index(ExpertRepository $expertRepository): Response
    {
        return $this->render('expert/index.html.twig', [
            'experts' => $expertRepository->findAll(),
        ]);
    }

    #[Route('/new', name: 'app_expert_new', methods: ['GET', 'POST'])]
    public function new(Request $request, ExpertRepository $expertRepository): Response
    {
        $expert = new Expert();
        $form = $this->createForm(ExpertType::class, $expert);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $expertRepository->save($expert, true);

            return $this->redirectToRoute('app_expert_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('expert/new.html.twig', [
            'expert' => $expert,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_expert_show', methods: ['GET'])]
    public function show(Expert $expert): Response
    {
        return $this->render('expert/show.html.twig', [
            'expert' => $expert,
        ]);
    }

    #[Route('/{id}/edit', name: 'app_expert_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Expert $expert, ExpertRepository $expertRepository): Response
    {
        $form = $this->createForm(ExpertType::class, $expert);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $expertRepository->save($expert, true);

            return $this->redirectToRoute('app_expert_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('expert/edit.html.twig', [
            'expert' => $expert,
            'form' => $form,
        ]);
    }

    #[Route('/{id}', name: 'app_expert_delete', methods: ['POST'])]
    public function delete(Request $request, Expert $expert, ExpertRepository $expertRepository): Response
    {
        if ($this->isCsrfTokenValid('delete'.$expert->getId(), $request->request->get('_token'))) {
            $expertRepository->remove($expert, true);
        }

        return $this->redirectToRoute('app_expert_index', [], Response::HTTP_SEE_OTHER);
    }
  /**
     * @Route("/search", name="search")
     */
    public function search(Request $request, ExpertRepository $expertRepository): Response
    {
        $form = $this->createForm(SearchType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $query = $form->get('query')->getData();
            $experts = $expertRepository->searchByName($query);

            return $this->render('expert/search.html.twig', [
                'experts' => $experts
            ]);
        }

        return $this->render('expert/search.html.twig', [
            'form' => $form->createView()
        ]);
    }
}
